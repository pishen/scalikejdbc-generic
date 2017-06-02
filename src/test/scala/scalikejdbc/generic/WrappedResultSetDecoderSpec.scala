package scalikejdbc.generic

import org.scalatest._
import scalikejdbc._

class WrappedResultSetDecoderSpec extends FlatSpec {
  case class Vocaloid(name: String, birthday: String, release_year: Int, height: Option[Int])

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")

  implicit val session = AutoSession

  sql"""
    CREATE TABLE vocaloids (
      name VARCHAR,
      birthday VARCHAR,
      release_year INT,
      height INT
    )
    """.execute.apply()

  sql"""
    INSERT INTO vocaloids VALUES
    ('Hatsune Miku', '08-31', 2007, 158),
    ('IA', '01-27', 2012, null),
    ('Otomachi Una', '07-30', 2016, 148)
    """.update.apply()

  "WrappedResultSetDecoder" should "decode WrappedResultSet to case class" in {
    val res = sql"SELECT * FROM vocaloids".decodeTo[Vocaloid].list.apply()

    val ans = Set(
      Vocaloid("Hatsune Miku", "08-31", 2007, Some(158)),
      Vocaloid("IA", "01-27", 2012, None),
      Vocaloid("Otomachi Una", "07-30", 2016, Some(148))
    )

    assert(res.toSet == ans)
  }

  it should "decode WrappedResultSet to Tuple" in {
    val res = sql"SELECT name, height FROM vocaloids".decodeTo[(String, Option[Int])].list.apply()

    val ans = Set(
      "Hatsune Miku" -> Some(158),
      "IA" -> None,
      "Otomachi Una" -> Some(148)
    )

    assert(res.toSet == ans)
  }

  it should "decode WrappedResultSet to single type" in {
    val res = sql"SELECT name FROM vocaloids".decodeTo[String].list.apply()

    val ans = Set("Hatsune Miku", "IA", "Otomachi Una")

    assert(res.toSet == ans)
  }
}
