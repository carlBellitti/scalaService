package com.example

//#user-registry-actor
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{ Actor, ActorLogging, Props }
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.onSuccess
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import com.example.QuickstartServer.{ log, userRegistryActor }
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.util.{ Failure, Success }

//#user-case-classes
final case class User(id: Int, name: String, age: Int, countryOfResidence: String)
final case class Users(users: Seq[User])
//#user-case-classes

object UserRegistryActor {
  final case class ActionPerformed(description: String)
  final case object GetUsers
  final case class CreateUser(user: User)
  final case class GetUser(name: String)
  final case class DeleteUser(name: String)
  final case class InitializeUsers(allUsers: Set[User])

  def props: Props = Props[UserRegistryActor]
}

class UserRegistryActor extends Actor with ActorLogging {
  import UserRegistryActor._

  var users = Set.empty[User]
  var testUser = User(100, "Joe Schmoe", 42, "Italy")
  // Initialize users with values from Database when app first launches
  // Consider moving the onComplete stuff later
  val u = DBUtils.getUsers

  u onComplete {
    case Success(results) => {
      results.foreach {
        case (id, name, age, countryOfOrigin) =>
          println("id: " + id + ", name: " + name + ", age: " + age + ", country: " + countryOfOrigin)
          val user = User(id, name, age, countryOfOrigin)
          users += user
      }
      println("Here a the users")
    }
    case Failure(e) => {
      println("Error " + e.getMessage)
    }
  }

  //val db = Database.forConfig("mysql")
  //val tableUsers: TableQuery[UserTable] = TableQuery[UserTable]

  def receive: Receive = {
    case GetUsers =>
      sender() ! Users(users.toSeq)
    case CreateUser(user) =>
      users += user
      sender() ! ActionPerformed(s"User ${user.name} created.")
    case GetUser(name) =>
      sender() ! users.find(_.name == name)
    case DeleteUser(name) =>
      users.find(_.name == name) foreach { user => users -= user }
      sender() ! ActionPerformed(s"User ${name} deleted.")
    case InitializeUsers(allUsers) =>
      users = allUsers
      sender() ! ActionPerformed(s"Users initialized.")
  }
}
//#user-registry-actor