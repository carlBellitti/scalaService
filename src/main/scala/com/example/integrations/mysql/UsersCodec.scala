package com.example.integrations.mysql

import com.example._


final case class UserRow(
  id: Int,
  name: String,
  age: Int,
  countryOfResidence: String)

final case class UserRows(users: Seq[UserRow])

object UserRow {
  def fromUser(user: User) = UserRow(
    user.id.value,
    user.name.value,
    user.age.value,
    user.countryOfResidence.value)

  def toUser(userRow: UserRow) = User(
    UserId(userRow.id),
    UserName(userRow.name),
    UserAge(userRow.age),
    UserCountry(userRow.countryOfResidence)
  )
}