import scala.collection.mutable.ListBuffer

trait UserRepo {
  def addUser(user: User): Unit

  def deleteUser(userName: String): Unit

  def getUser(userName: String): User

  def getAllUsers: ListBuffer[User]
}


