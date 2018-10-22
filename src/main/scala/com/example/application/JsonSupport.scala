package com.example.application

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.UserRegistryActor.ActionPerformed
import com.example.integrations.web.{UserJson, UsersJson}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  // import the default encoders for primitive types (Int, String, Lists etc)
  // import DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat4(UserJson)
  implicit val usersJsonFormat = jsonFormat1(UsersJson)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
