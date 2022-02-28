import spray.json.{DefaultJsonProtocol, JsArray, JsString, JsValue, JsonFormat, RootJsonFormat, enrichAny}

import java.util.UUID
import scala.collection.mutable.ListBuffer

case class User(name: String)

object UserProtocol extends DefaultJsonProtocol {
  implicit def listBufferFormat[T: JsonFormat] = new RootJsonFormat[ListBuffer[T]] {
    override def write(obj: ListBuffer[T]): JsValue = JsArray(obj.map(_.toJson).toVector)

    override def read(json: JsValue): ListBuffer[T] = ???
  }

  implicit val userFormat = jsonFormat1(User)
}


