package edu.knoldus

import edu.knoldus.model.User
import org.scalatest.flatspec.AnyFlatSpec

class UserRepoImplTest extends AnyFlatSpec {

  behavior of "UserRepoImplTest"
  val userRepo = new UserRepoImpl
  val prakhar: User = User(name = "Prakhar", age = 24)

  it should "addUser" in {
    userRepo.addUser(prakhar)
    assertResult(prakhar)(userRepo.getUserByName("Prakhar").get)
  }

  it should "getUser" in {
    val newUser = User(name = "User", age = 34)
    userRepo.addUser(newUser)
    val id = newUser.id
    assertResult(newUser)(userRepo.getUser(id).get)
  }

  it should "getUserByName" in {
    assertResult(prakhar)(userRepo.getUserByName("Prakhar").get)
  }

  it should "deleteUser" in {
    val userTwo = User(name = "User2", age = 30)
    userRepo.addUser(userTwo)
    assertResult(userTwo)(userRepo.deleteUser(userTwo.name).get)
  }

  it should "getAllUsers" in {
    val size = 2
    assertResult(size)(userRepo.getAllUsers.size)
  }
}
