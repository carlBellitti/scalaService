package com.example.web

import com.example.UserRegistryActor.ActionPerformed
import spray.json.DefaultJsonProtocol._

final case class UserJson(
  id: Int,
  name: String,
  age: Int,
  countryOfResidence: String)

final case class UsersJson(users: Seq[UserJson])

object Codecs {

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)

}

object UserJson {

  import com.example.{User, Users, UserId, UserName, UserAge, UserCountry}

  implicit val userJsonFormat = jsonFormat4(UserJson.apply)

  def fromUser(user: User) = UserJson(
    user.id.value,
    user.name.value,
    user.age.value,
    user.countryOfResidence.value)

  def fromUsers(users: Users): Set[UserJson] = {
    var usersSet = Set.empty[UserJson]
    users.users.map {
      case User(id: UserId, name:UserName, age:UserAge, country:UserCountry) =>
        usersSet += UserJson(id.value, name.value, age.value, country.value)
    }
    usersSet
  }
}