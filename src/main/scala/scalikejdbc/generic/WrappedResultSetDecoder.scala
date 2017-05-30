package scalikejdbc.generic

import scalikejdbc._
import shapeless._
import shapeless.labelled._

trait WrappedResultSetDecoder[A] {
  def decode(rs: WrappedResultSet): A
}

object WrappedResultSetDecoder {
  def apply[A](implicit decoder: WrappedResultSetDecoder[A]) = decoder
  
  def createDecoder[A](f: WrappedResultSet => A) = new WrappedResultSetDecoder[A] {
    def decode(rs: WrappedResultSet) = f(rs)
  }

  implicit def singleDecoder[A : TypeBinder]: WrappedResultSetDecoder[A] = createDecoder {
    rs => rs.get[A](1)
  }

  implicit val hnilDecoder: WrappedResultSetDecoder[HNil] = createDecoder {
    rs => HNil
  }

  implicit def hlistDecoder[K <: Symbol, H : TypeBinder, T <: HList](
    implicit
    witness: Witness.Aux[K],
    tDecoder: WrappedResultSetDecoder[T]
  ): WrappedResultSetDecoder[FieldType[K, H] :: T] = {
    val fieldName = witness.value.name
    val tupleIndex = """_(\d+)""".r
    createDecoder { rs =>
      fieldName match {
        case tupleIndex(i) =>
          field[K](rs.get[H](i.toInt)) :: tDecoder.decode(rs)
        case _ =>
          field[K](rs.get[H](fieldName)) :: tDecoder.decode(rs)
      }
    }
  }

  implicit def genericDecoder[A, H <: HList](
    implicit
    generic: LabelledGeneric.Aux[A, H],
    hDecoder: WrappedResultSetDecoder[H]
  ): WrappedResultSetDecoder[A] = createDecoder { rs =>
    generic.from(hDecoder.decode(rs))
  }
}

