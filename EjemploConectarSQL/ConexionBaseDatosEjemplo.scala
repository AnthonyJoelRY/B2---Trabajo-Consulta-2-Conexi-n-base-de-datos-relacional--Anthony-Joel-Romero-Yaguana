import slick.jdbc.MySQLProfile.api._
// Importa la API de Slick para trabajar con bases de datos MySQL.

import scala.concurrent.Await
import scala.concurrent.duration._
// Importa las clases necesarias para manejar operaciones asincrónicas con tiempo de espera definido.

object ConexionBaseDatosEjemplo {
  // Define el objeto principal donde se ejecutará el programa.

  // Caso clase para representar una película
  case class Pelicula(
                       id: Int,                  // Identificador único de la película.
                       titulo: String,           // Título de la película.
                       director: String,         // Nombre del director de la película.
                       anio: Int,                // Año de lanzamiento de la película.
                       genero: String,           // Género de la película.
                       duracion: Int             // Duración de la película en minutos.
                     )

  // Definición de la tabla
  class Peliculas(tag: Tag) extends Table[Pelicula](tag, "peliculas") {
    // Define la estructura de la tabla "peliculas" en la base de datos.

    def id = column[Int]("id", O.PrimaryKey)
    // Columna "id" que es la clave primaria.

    def titulo = column[String]("titulo")
    // Columna "titulo" que almacena el título de la película.

    def director = column[String]("director")
    // Columna "director" que almacena el nombre del director.

    def anio = column[Int]("anio")
    // Columna "anio" que almacena el año de lanzamiento.

    def genero = column[String]("genero")
    // Columna "genero" que almacena el género de la película.

    def duracion = column[Int]("duracion")
    // Columna "duracion" que almacena la duración en minutos.

    def * = (id, titulo, director, anio, genero, duracion) <> (Pelicula.tupled, Pelicula.unapply)
    // Mapea las columnas de la tabla a la clase "Pelicula" y viceversa.
  }

  def main(args: Array[String]): Unit = {
    // Punto de entrada principal del programa.

    val db = Database.forConfig("mysqlDB")
    // Crea una instancia de la conexión a la base de datos utilizando la configuración "mysqlDB" en el archivo `application.conf`.

    val peliculas = TableQuery[Peliculas]
    // Crea una consulta inicial sobre la tabla "peliculas".

    try {
      // Bloque `try` para manejar posibles excepciones durante la ejecución del código.

      val query = peliculas.result
      // Define una consulta para obtener todos los registros de la tabla "peliculas".

      val result = Await.result(db.run(query), 10.seconds)
      // Ejecuta la consulta en la base de datos y espera hasta 10 segundos para obtener los resultados.

      println("Resultados de la tabla 'peliculas':")
      // Imprime un encabezado para indicar que se mostrarán los resultados de la tabla.

      result.foreach { pelicula =>
        // Itera sobre los resultados de la consulta y realiza una acción para cada película.

        println(s"ID: ${pelicula.id}, Título: ${pelicula.titulo}, Director: ${pelicula.director}, Año: ${pelicula.anio}, Género: ${pelicula.genero}, Duración: ${pelicula.duracion} min")
        // Imprime los detalles de cada película en un formato legible.
      }
    } finally {
      db.close()
      // Cierra la conexión con la base de datos, independientemente de si ocurrió un error o no.
    }
  }
}
