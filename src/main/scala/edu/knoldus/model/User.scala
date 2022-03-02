package edu.knoldus.model

import spray.json.{DefaultJsonProtocol, JsArray, JsBoolean, JsNull, JsNumber, JsObject, JsString, JsValue, JsonFormat, RootJsonFormat, enrichAny}

import java.util.UUID
import scala.collection.mutable.ListBuffer

case class User(id: UUID = UUID.randomUUID(), name: String, age: Int)

object UserProtocol extends DefaultJsonProtocol {
  implicit val listBufferFormat: JsonFormat[ListBuffer[User]] = new JsonFormat[ListBuffer[User]] {
    override def write(obj: ListBuffer[User]): JsValue = JsArray(obj.map(_.toJson).toVector)

    override def read(json: JsValue): ListBuffer[User] = json match {
      case JsArray(elements) => elements.map(_.convertTo[User]).to(ListBuffer)
      case _ => throw new Exception("Error in deserialization of ListBuffer")
    }
  }

  implicit def uuidFormat: JsonFormat[UUID] = new JsonFormat[UUID] {
    override def write(obj: UUID): JsValue = JsString(obj.toString)

    override def read(json: JsValue): UUID = json match {
      case JsString(value) => UUID.fromString(value)
      case _ => throw new Exception("Error in deserialization of UUID")
    }
  }

  implicit val userFormat: RootJsonFormat[User] = jsonFormat3(User)

}


