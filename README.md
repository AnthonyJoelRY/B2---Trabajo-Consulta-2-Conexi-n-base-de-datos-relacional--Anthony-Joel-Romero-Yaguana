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

3. Usa el comando `sbt update` para descargar las dependencias.

---
