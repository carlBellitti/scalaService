package com.example.integrations.web
import scala

final case class UserJson(
  id: Int,
  name: String,
  age: Int,
  countryOfResidence: String)

final case class UsersJson(users: Seq[UserJson])

object UserJson {

  import com.example.{User, Users}

  def fromUser(user: User) = UserJson(
    user.id.value,
    user.name.value,
    user.age.value,
    user.countryOfResidence.value)
  // map(_.map(MemberChangeTypeRow.toMemberChangeType))
  def fromUsers(users: Users): UsersJson = {
    users.foreach((user: User) => println(user))

    }

  }
}