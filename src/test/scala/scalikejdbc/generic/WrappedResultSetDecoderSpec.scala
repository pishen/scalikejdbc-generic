package scalikejdbc.generic

import org.scalatest._
import shapeless._
import scalikejdbc._

class WrappedResultSetDecoderSpec extends FlatSpec {
  case class Vocaloid(name: String, year: Int, birthday: Option[String])

  "Decoder" should "decode a HList" in {
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")
    
    implicit val session = AutoSession
    
    sql"""
      CREATE TABLE vocaloids (
        name VARCHAR,
        birthday VARCHAR,
        year INT
      )
      """.execute.apply()
    
    sql"""
      INSERT INTO vocaloids VALUES
      ('Hatsune Miku', '08-31', 2007),
      ('IA', '01-27', 2012),
      ('Otomachi Una', null, 2016)
      """.update.apply()
    
    //val gen = LabelledGeneric[Vocaloid]
    //val dec = WrappedResultSetDecoder[Vocaloid]
    //val dec = WrappedResultSetDecoder[(String, Int)]
    val dec = WrappedResultSetDecoder[String]
    
    val res = sql"""
      SELECT name,year FROM vocaloids
      """.map(rs => dec.decode(rs)).list.apply()
    
    res.foreach(println)
  }
}
