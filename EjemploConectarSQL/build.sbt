ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "Clases",
    libraryDependencies ++=Seq("mysql" % "mysql-connector-java" % "8.0.33", // Conector JDBC para MySQL
      "com.typesafe.slick" %% "slick" % "3.4.0",          // Framework Slick para bases de datos
      "com.typesafe.slick" %% "slick-hikaricp" % "3.4.0", // HikariCP para pool de conexiones
      "org.slf4j" % "slf4j-simple" % "2.0.9", "com.typesafe" % "config" % "1.4.2"            // Logger para depuración


    )
  )
