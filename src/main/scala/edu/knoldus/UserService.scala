package edu.knoldus

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import edu.knoldus.model.User
import edu.knoldus.model.UserProtocol._
import spray.json.enrichAny

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn

object UserService extends App {
  implicit val userSystem: ActorSystem = ActorSystem("userSystem")
  implicit val executionContext: ExecutionContext = userSystem.dispatcher

  val userRepo: UserRepo = new UserRepoImpl()

  val route = {
    pathSingleSlash {
      get {
        complete("Welcome to User Service")
      }
    } ~
      path("getAllUsers") {
        get {
          val users = userRepo.getAllUsers
          if (users.isEmpty)
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<p>No user Found</p>"))
          else //complete(HttpEntity(ContentTypes.`application/json`, userRepo.getAllUsers.toList.toJson.prettyPrint))
            complete(HttpEntity(ContentTypes.`application/json`, userRepo.getAllUsers.toJson.prettyPrint))
        }
      } ~
      path("getUserByName" / Segment) { userName =>
        get {
          val user = userRepo.getUserByName(userName)
          val result = user match {
            case Some(value) => value.toJson.prettyPrint
            case None => "<p>No user by that Name</p>"
          }
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, result))

        }
      } ~
      path("getUser" / JavaUUID) { userId =>
        get {
          val user = userRepo.getUser(userId)
          val result = user match {
            case Some(value) => value.toJson.prettyPrint
            case None => "<p>No user by that ID</p>"
          }
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, result))

        }
      } ~
      path("addUser") {
        post {
          parameter("name", "age".as[Int]) { (name, age) =>
            val newUser = User(name = name, age = age)
            userRepo.addUser(newUser)
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<p>Added User with name: $name.</p>"))
          }
        }
      } ~
      path("deleteUser") {
        delete {
          parameter("name") { name =>
            val deletedUser = userRepo.deleteUser(name)
            val result = deletedUser match {
              case Some(value) => s"<p>Deleted User with name: ${value.name}</p>"
              case None => s"</p>No user by that name found<p>"
            }
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, result))
          }
        }
      }
  }

  val serverBinding: Future[Http.ServerBinding] = Http().newServerAt("localhost", 9000).bindFlow(route)
  println("Server started at http://localhost:9000\nPress RETURN to EXIT")
  StdIn.readLine()
  serverBinding.flatMap(_.unbind()).onComplete(_ => userSystem.terminate())
}
