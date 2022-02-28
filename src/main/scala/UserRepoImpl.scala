import scala.collection.mutable.ListBuffer

class UserRepoImpl extends UserRepo {
  var users: ListBuffer[User] = ListBuffer.empty

  override def addUser(user: User): Unit = users.append(user)

  override def deleteUser(name: String): Unit = {
    users = users.filter(_.name != name)
  }

  override def getUser(userName: String): User = users.filter(_.name == userName).head

  override def getAllUsers: ListBuffer[User] = users
}


