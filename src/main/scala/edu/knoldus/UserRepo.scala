package edu.knoldus

import edu.knoldus.model.User

import scala.collection.mutable.ListBuffer

trait UserRepo {
  def addUser(user: User): Unit

  def deleteUser(userName: String): Unit

  def getUserByName(userName: String): User

  def getUser(userId: String): User

  def getAllUsers: ListBuffer[User]
}
