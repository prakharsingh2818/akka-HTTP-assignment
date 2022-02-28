import UserProtocol._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import spray.json.enrichAny

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn

object UserService extends App {
  implicit val userSystem: ActorSystem = ActorSystem("userSystem")
  //implicit val materializer: ActorMaterializer = ActorMaterializer()
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
          complete(HttpEntity(ContentTypes.`application/json`, userRepo.getAllUsers.toJson(listBufferFormat).prettyPrint))
        }
      } ~
      path("getUser") {
        get {
          parameter("name") { name =>
            complete(HttpEntity(ContentTypes.`application/json`, userRepo.getUser(name).toJson.prettyPrint))
          }
        }
      } ~
      path("addUser") {
        post {
          parameter("name") { name =>
            val newUser = User(name)
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
