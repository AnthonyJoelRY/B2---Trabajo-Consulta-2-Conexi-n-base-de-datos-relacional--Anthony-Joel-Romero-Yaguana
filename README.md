# B2 - Trabajo-Consulta-2-Conexi-n-base-de-datos-relacional - Anthony-Joel-Romero-Yaguana
# JDBC y Conexión a Bases de Datos en Scala

## **¿Qué es JDBC?**

**JDBC (Java Database Connectivity)** es una API de Java que permite a las aplicaciones interactuar con bases de datos relacionales. Proporciona un conjunto de interfaces y clases para:

- Enviar consultas SQL.
- Recuperar resultados.
- Manejar datos de forma eficiente.

### **Componentes principales de JDBC**

1. **Driver Manager**: Gestiona los controladores de bases de datos y establece conexiones.
2. **Driver**: Traduce las llamadas JDBC al protocolo específico del proveedor de la base de datos.
3. **Connection**: Representa una conexión activa con una base de datos. Permite ejecutar comandos SQL.
4. **Statement**: Permite enviar consultas SQL. Subtipos:
   - **Statement**: Para consultas SQL simples.
   - **PreparedStatement**: Para consultas parametrizadas.
   - **CallableStatement**: Para procedimientos almacenados.
5. **ResultSet**: Representa el conjunto de resultados de una consulta SQL.
6. **SQLException**: Maneja errores relacionados con bases de datos.

---

## **Librerías de Scala para conectarse a una base de datos relacional**

Scala ofrece varias librerías para interactuar con bases de datos relacionales. Aquí se comparan dos de las más populares: **Slick** y **Doobie**.

| **Librería** | **Características**                                                                                                          | **Importación**                                                                                   |
|--------------|------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| **Slick**    | - API funcional y orientada a datos.                                                                                         | `libraryDependencies += "com.typesafe.slick" %% "slick" % "3.4.1"`                                |
|              | - Soporta múltiples bases de datos (MySQL, PostgreSQL, SQLite, etc.).                                                        | `libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1"`                      |
|              | - Generación automática de consultas SQL a partir de código Scala.                                                           |                                                                                                   |
|              | - Integra con Akka Streams para procesamiento de flujos de datos.                                                            |                                                                                                   |
| **Doobie**   | - API funcional basada en **Tagless Final** y compatible con bibliotecas de efectos (e.g., Cats Effect, ZIO).                | `libraryDependencies += "org.tpolecat" %% "doobie-core" % "1.0.0-RC2"`                            |
|              | - Permite el manejo manual y flexible de consultas SQL.                                                                      | `libraryDependencies += "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC2"`                          |
|              | - Validación de consultas SQL en tiempo de compilación.                                                                      |                                                                                                   |
|              | - Soporte para múltiples bases de datos y herramientas de integración con Cats y ZIO.                                        |                                                                                                   |

---

## **Cómo importar estas librerías**

1. Asegúrate de tener un archivo `build.sbt` configurado para tu proyecto.
2. Añade las dependencias necesarias según la librería que elijas:

### **Para Slick:**
```scala
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
  "org.slf4j" % "slf4j-nop" % "2.0.9" // Logger opcional
)
```

### **Para Doobie:**
```scala
libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core" % "1.0.0-RC2",
  "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC2",
  "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC2", // Para PostgreSQL (opcional)
  "org.tpolecat" %% "doobie-specs2" % "1.0.0-RC2" % Test // Para pruebas
)
```
### **Conectar una base de datos SQL a un proyecto de Scala:(Capturas de pantalla de los resultados)**
![image](https://github.com/user-attachments/assets/56e3cfde-ee70-4c00-9fa5-2b111c3723ac)

![image](https://github.com/user-attachments/assets/432fb1c3-324a-42c2-a9d9-05d2a081758e)

![image](https://github.com/user-attachments/assets/d4c69d66-c076-4dc8-8920-137805db7d53)

### **Codigo en Scala:**
### Base de datos SQL:
```
-- Crear la base de datos
CREATE DATABASE peliculas_db;

-- Usar la base de datos
USE peliculas_db;

-- Crear la tabla "peliculas"
CREATE TABLE peliculas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    director VARCHAR(255),
    anio INT,
    genero VARCHAR(100),
    duracion INT COMMENT 'Duración en minutos'
);

-- Insertar datos de prueba
INSERT INTO peliculas (titulo, director, anio, genero, duracion) VALUES
('Inception', 'Christopher Nolan', 2010, 'Ciencia Ficción', 148),
('Titanic', 'James Cameron', 1997, 'Drama/Romance', 195),
('The Matrix', 'Lana Wachowski, Lilly Wachowski', 1999, 'Ciencia Ficción', 136),
('Pulp Fiction', 'Quentin Tarantino', 1994, 'Crimen/Drama', 154),
('The Godfather', 'Francis Ford Coppola', 1972, 'Crimen/Drama', 175);

-- Consultar los datos de prueba
SELECT * FROM peliculas;
```
### Extración y consulta:
```scala
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
```
### Configuración:
```scala
mysqlDB = {
  url = "jdbc:mysql://127.0.0.1:3306/peliculas_db"
  driver = "com.mysql.cj.jdbc.Driver"
  user = "root"
  password = "05291325"
  connectionPool = "HikariCP"
}

```

# Descripción de Funciones

## Funciones Principales

### `case class Pelicula`
- **Descripción**: Representa los datos de una película como un objeto Scala.
- **Atributos**:
  - `id`: Identificador único de la película.
  - `titulo`: Título de la película.
  - `director`: Nombre del director de la película.
  - `anio`: Año de lanzamiento de la película.
  - `genero`: Género de la película.
  - `duracion`: Duración de la película en minutos.

---

### `class Peliculas`
- **Descripción**: Define la estructura de la tabla `peliculas` en la base de datos.
- **Características**:
  - Mapea las columnas de la tabla SQL a los atributos de la clase `Pelicula`.
  - Proporciona un método `*` para convertir entre objetos `Pelicula` y filas de la tabla.

---

### `main`
- **Descripción**: Es el punto de entrada del programa.
- **Responsabilidades**:
  - Establece la conexión con la base de datos utilizando la configuración definida.
  - Ejecuta la consulta para obtener todas las películas de la tabla `peliculas`.
  - Imprime los resultados en la consola.
  - Cierra la conexión con la base de datos al finalizar.

---

### `peliculas.result`
- **Descripción**: Genera una consulta para recuperar todos los registros de la tabla `peliculas`.

---

### `Await.result`
- **Descripción**: Bloquea la ejecución del programa hasta que la consulta a la base de datos se complete o expire el tiempo de espera.

---

### `db.close`
- **Descripción**: Cierra la conexión con la base de datos para liberar los recursos asociados.

---
