package edu.knoldus

import edu.knoldus.model.User

import java.util.UUID
import scala.collection.mutable.ListBuffer

trait UserRepo {

  def addUser(user: User): Unit

  def deleteUser(userName: String): Option[User]

  def getUserByName(userName: String): Option[User]

  def getUser(userId: UUID): Option[User]

  def getAllUsers: ListBuffer[User]
}
