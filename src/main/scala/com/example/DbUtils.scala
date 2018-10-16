package com.example
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.util.{ Failure, Success }

object DBUtils {

  val db = Database.forConfig("mysql")

  def getUsers: Future[Seq[(Int, String, Int, String)]] = {

    val tableUsers: TableQuery[UserTable] = TableQuery[UserTable]
    val filterQuery: Query[UserTable, (Int, String, Int, String), Seq] = tableUsers.filter(_.age > 0)
    println("Generated SQL for filter query:\n" + filterQuery.result.statements)
    db.run(filterQuery.result)
  }

  //    def parseUsersFromResponse(f: Future[Seq[(Int, String, Int, String)]]): Set[Users] = {
  //      val users = Set.empty[Users]
  //      f onComplete {
  //
  //        case Success(results) => {
  //
  //          results.foreach {
  //            case (id, name, age, countryOfOrigin) =>
  //              println("id: " + id + ", name: " + name + ", age: " + age + ", country: " + countryOfOrigin)
  //              val userToAdd = User(id, name, age, countryOfOrigin)
  //              //users += userToAdd
  //
  //          }
  //          println("Here a the " + users)
  //        }
  //        case Failure(e) => {
  //          println("Error " + e.getMessage)
  //        }
  //      }
  //      users
  //    }
}

