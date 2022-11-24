CREATE DATABASE Tenistas;
USE Tenistas;
CREATE TABLE Tenistas
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    nacionalidad VARCHAR(50)
);
CREATE TABLE discos
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(50) NOT NULL,
    ventas FLOAT DEFAULT 0,
    id_cantante INT UNSIGNED,
    INDEX (id_cantante),
    FOREIGN KEY (id_cantante)
        REFERENCES Tenistas (id)
);
CREATE TABLE partidos
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(50) NOT NULL UNIQUE,
    duracion INT DEFAULT 0,
    id_cantante INT UNSIGNED,
    INDEX (id_cantante),
    FOREIGN KEY (id_cantante)
        REFERENCES Tenistas (id),
    id_disco INT UNSIGNED,
    INDEX (id_disco),
    FOREIGN KEY (id_disco)
        REFERENCES discos (id)
);
CREATE TABLE usuarios
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(50) NOT NULL,
    rol VARCHAR(50) NOT NULL
);
