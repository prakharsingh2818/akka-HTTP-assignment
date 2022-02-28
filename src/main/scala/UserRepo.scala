import scala.collection.mutable.ListBuffer

trait UserRepo {
  def addUser(user: User)
  def deleteUser(userName: String)
  def getUser(userName: String): User
  def getAllUsers(): ListBuffer[User]
}


