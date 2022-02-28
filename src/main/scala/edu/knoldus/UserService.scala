package edu.knoldus

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import edu.knoldus.model.User
import edu.knoldus.model.UserProtocol._
import spray.json.enrichAny

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
          complete(HttpEntity(ContentTypes.`application/json`, userRepo.getAllUsers.toJson.prettyPrint))
        }
      } ~
      path("getUserByName" / Segment) { userName =>
        get {
          complete(HttpEntity(ContentTypes.`application/json`, userRepo.getUserByName(userName).toJson.prettyPrint))
        }
      } ~
      path("getUser" / Segment) { userId =>
        get {
          complete(HttpEntity(ContentTypes.`application/json`, userRepo.getUser(userId).toJson.prettyPrint))

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
            userRepo.deleteUser(name)
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<p>Deleted User with name: $name.</p>"))
          }
        }
      }
  }

  val serverBinding: Future[Http.ServerBinding] = Http().newServerAt("localhost", 9000).bindFlow(route)
  println("Server started at http://localhost:9000\nPress RETURN to EXIT")
  StdIn.readLine()
  serverBinding.flatMap(_.unbind()).onComplete(_ => userSystem.terminate())
}
