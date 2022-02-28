package edu.knoldus

import edu.knoldus.model.User

import scala.collection.mutable.ListBuffer

class UserRepoImpl extends UserRepo {
  var users: ListBuffer[User] = ListBuffer.empty

  override def addUser(user: User): Unit = users.append(user)

  override def deleteUser(name: String): Option[User] = {
    val deletedUser = getUserByName(name)
    users = users.filter(_.name != name)
    deletedUser
  }


  override def getUserByName(userName: String): Option[User] = users.find(_.name == userName)

  override def getUser(userId: String): Option[User] = users.find(_.id == userId)


  override def getAllUsers: ListBuffer[User] = users
}


