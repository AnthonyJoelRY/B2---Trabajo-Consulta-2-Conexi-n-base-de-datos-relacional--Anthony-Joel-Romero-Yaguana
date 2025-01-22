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