package scalikejdbc

package object generic {
  implicit class SQLWrapper[A, E <: WithExtractor](sql: SQL[A, E]) {
    def decodeTo[T](implicit dec: WrappedResultSetDecoder[T]) = {
      sql.map(rs => dec.decode(rs))
    }
  }
}
