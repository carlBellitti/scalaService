package com.example.integrations.web

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.UserRegistryActor.ActionPerformed
import com.example.{User, Users}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat4(UserJson)
  implicit val usersJsonFormat = jsonFormat1(UsersJson)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
