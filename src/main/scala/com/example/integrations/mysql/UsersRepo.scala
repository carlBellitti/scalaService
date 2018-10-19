package com.example.integrations.mysql

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

object DBUtils {

  val db = Database.forConfig("mysql")

  def getUsers: Future[Seq[UserRow]] = {

    val tableUsers: TableQuery[UserTable] = TableQuery[UserTable]
    val filterQuery: Query[UserTable, UserRow, Seq] = tableUsers.filter(_.age > 0)
    println("Generated SQL for filter query:\n" + filterQuery.result.statements)
    db.run(filterQuery.result)
  }

}

