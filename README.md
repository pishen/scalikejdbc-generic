# scalikejdbc-generic

A generic decoder which helps mapping your [ScalikeJDBC](http://scalikejdbc.org/) `WrappedResultSet` into the data structure you want.

``` scala
import scalikejdbc._
import scalikejdbc.generic._

case class Vocaloid(name: String, birthday: String, release_year: Int, height: Option[Int])

val res0 = sql"SELECT height, release_year, birthday, name FROM vocaloids".decodeTo[Vocaloid].list.apply()
//res0: List(Vocaloid(Hatsune Miku,08-31,2007,Some(158)), ...)

val res1 = sql"SELECT name, height FROM vocaloids".decodeTo[(String, Option[Int])].list.apply()
//res1: List((Hatsune Miku,Some(158)), ...)

val res2 = sql"SELECT name FROM vocaloids".decodeTo[String].list.apply()
//res2: List(Hatsune Miku, ...)
```

## Installation

``` scala
resolvers += Resolver.bintrayRepo("pishen", "maven")

libraryDependencies += "net.pishen" %% "scalikejdbc-generic" % "0.1.0"
```
