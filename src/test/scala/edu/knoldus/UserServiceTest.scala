package edu.knoldus

import akka.http.scaladsl.testkit.ScalatestRouteTest
import edu.knoldus.UserService.route
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UserServiceTest extends AnyFlatSpec with Matchers with ScalatestRouteTest {

/*  "Get" should "get" in {
    Get() ~> route ~> check {
      responseAs[String] shouldEqual "Welcome"
    }
  }*/
}
