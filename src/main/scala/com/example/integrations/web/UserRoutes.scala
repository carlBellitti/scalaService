package com.example.integrations.web

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, concat, entity, getFromResource, getFromResourceDirectory, onSuccess, pathEnd, pathEndOrSingleSlash, pathPrefix, redirectToTrailingSlashIfMissing}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.{get, post}
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.util.Timeout
import akka.pattern.ask
import com.example.UserRegistryActor.{ActionPerformed, CreateUser, GetUsersJson}
import com.example.application.JsonSupport
import com.example.{User, Users}

import scala.concurrent.Future
import scala.concurrent.duration._


//#user-routes-class
trait UserRoutes extends JsonSupport {
  //#user-routes-class

  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[UserRoutes])

  // other dependencies that UserRoutes use
  def userRegistryActor: ActorRef

  // Required by the `ask` (?) method below
  implicit val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

//  val staticResources: Route =
//    (get & pathPrefix("client")) {
//      (pathEndOrSingleSlash & redirectToTrailingSlashIfMissing(StatusCodes.TemporaryRedirect)) {
//        getFromResource("client/index.html")
//      } ~ {
//        getFromResourceDirectory("client")
//      }
//    }

  //#all-routes
  //#users-get-post
  //#users-get-delete
  lazy val userRoutes: Route =
    pathPrefix("users") {
      concat(
        //#users-get-delete
        pathEnd {
          concat(
            get {
              val users: Future[UsersJson] =
                (userRegistryActor ? GetUsersJson).mapTo[UsersJson]
              complete(users)
            },
            post {
              entity(as[User]) { user =>
                val userCreated: Future[ActionPerformed] =
                  (userRegistryActor ? CreateUser(user)).mapTo[ActionPerformed]
                onSuccess(userCreated) { performed =>
                  log.info("Created user [{}]: {}", user.name, performed.description)
                  complete((StatusCodes.Created, performed))
                }
              }
            })
        }
        //#users-get-post
        //#users-get-delete
//        path(Segment) { name =>
//          concat(
//            get {
//              //#retrieve-user-info
//              val maybeUser: Future[Option[User]] =
//                (userRegistryActor ? GetUser(name)).mapTo[Option[User]]
//              rejectEmptyResponse {
//                complete(maybeUser)
//              }
//              //#retrieve-user-info
//            },
//            delete {
//              //#users-delete-logic
//              val userDeleted: Future[ActionPerformed] =
//                (userRegistryActor ? DeleteUser(name)).mapTo[ActionPerformed]
//              onSuccess(userDeleted) { performed =>
//                log.info("Deleted user [{}]: {}", name, performed.description)
//                complete((StatusCodes.OK, performed))
//              }
//              //#users-delete-logic
//            })
        //}
        )
      //#users-get-delete
    }// ~ staticResources
  //#all-routes
}
