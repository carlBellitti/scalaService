package com.example

//#user-registry-actor
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{ Actor, ActorLogging, Props }
import com.example.integrations.mysql._

import scala.util.{ Failure, Success }

final case class UserId(value: Int) extends AnyVal
final case class UserName(value: String) extends AnyVal
final case class UserAge(value: Int) extends AnyVal
final case class UserCountry(value: String) extends AnyVal

//#user-case-classes
final case class User(id: UserId, name: UserName, age: UserAge, countryOfResidence: UserCountry)
final case class Users(users: Set[User])

//#user-case-classes

object UserRegistryActor {
  final case class ActionPerformed(description: String)
  final case object GetUsersJson
//  final case class CreateUser(user: User)
//  final case class GetUser(name: String)
//  final case class DeleteUser(name: String)
//  final case class InitializeUsers(allUsers: Set[User])

  def props: Props = Props[UserRegistryActor]
}

class UserRegistryActor extends Actor with ActorLogging {
  import UserRegistryActor._
  import com.example.integrations.web.UserJson

  var users = Set.empty[User]
  var testUser = User(UserId(100), UserName("Joe Schmoe"), UserAge(42), UserCountry("Italy"))
  // Initialize users with values from Database when app first launches
  // Consider moving the onComplete stuff later
  val u = DBUtils.getUsers

  u onComplete {
    case Success(results) => {
      results.foreach {
        case UserRow(id: Int, name:String, age:Int, countryOfResidence:String) =>
          println("id: " + id + ", name: " + name + ", age: " + age + ", country: " + countryOfResidence)
          val user = User(UserId(id), UserName(name), UserAge(age), UserCountry(countryOfResidence))
          users += user
      }
      println("Here a the users")
    }
    case Failure(e) => {
      println("Error " + e.getMessage)
    }
  }

  def receive: Receive = {
    case GetUsersJson =>
      // sender() ! Users(users.toSeq)
      sender() ! UserJson.fromUsers(Users(users))
//    case CreateUser(user) =>
//      users += user
//      sender() ! ActionPerformed(s"User ${user.name} created.")
//    case GetUser(name) =>
//      sender() ! users.find(_.name == name)
//    case DeleteUser(name) =>
//      users.find(_.name == name) foreach { user => users -= user }
//      sender() ! ActionPerformed(s"User ${name} deleted.")
//    case InitializeUsers(allUsers) =>
//      users = allUsers
//      sender() ! ActionPerformed(s"Users initialized.")
  }
}
//#user-registry-actor