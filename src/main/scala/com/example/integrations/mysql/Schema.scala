package com.example.integrations.mysql

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

class UserTable(tag: Tag)
  extends Table[UserRow](tag, "users") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey)
  def name: Rep[String] = column[String]("name")
  def age: Rep[Int] = column[Int]("age")
  def countryOfResidence: Rep[String] = column[String]("countryOfResidence")

  def * : ProvenShape[UserRow] =
    (id, name, age, countryOfResidence).mapTo[UserRow]
}