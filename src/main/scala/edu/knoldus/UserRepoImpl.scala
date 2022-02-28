package edu.knoldus

import edu.knoldus.model.User

import scala.collection.mutable.ListBuffer

class UserRepoImpl extends UserRepo {
  var users: ListBuffer[User] = ListBuffer.empty

  override def addUser(user: User): Unit = users.append(user)

  override def deleteUser(name: String): Unit = {
    users = users.filter(_.name != name)
  }

  override def getUserByName(userName: String): User = users.filter(_.name == userName).head

  override def getUser(userId: String): User = users.filter(_.id == userId).head

  override def getAllUsers: ListBuffer[User] = users
}


