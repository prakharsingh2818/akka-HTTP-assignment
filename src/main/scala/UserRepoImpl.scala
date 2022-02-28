import spray.json.{DefaultJsonProtocol, JsArray, JsValue, JsonFormat, RootJsonFormat, enrichAny}

import scala.collection.mutable.ListBuffer

class UserRepoImpl extends UserRepo {
  var users: ListBuffer[User] = ListBuffer.empty

  override def addUser(user: User): Unit = users.append(user)

  override def deleteUser(name: String): Unit = {
    users = users.filter(_.name != name)
  }

  override def getUser(userName: String): User = users.filter(_.name == userName)(0)

  override def getAllUsers(): ListBuffer[User] = users

  implicit def listBufferFormat[T: JsonFormat] = new RootJsonFormat[ListBuffer[T]] {
    override def write(obj: ListBuffer[T]): JsValue = JsArray(obj.map(_.toJson).toVector)

    override def read(json: JsValue): ListBuffer[T] = ???
  }
}


