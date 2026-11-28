CREATE DATABASE IF NOT EXISTS skillup_db;
USE skillup_db;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    tipo_usuario ENUM('ADMIN_TUTOR', 'ESTUDIANTE') NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    foto MEDIUMBLOB,
    foto_tipo VARCHAR(50),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE materia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    area VARCHAR(100)
);

CREATE TABLE categoria_recurso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE perfil_tutor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT UNIQUE NOT NULL,
    id_materia INT NOT NULL,
    nivel VARCHAR(50),
    precio_base DECIMAL(10, 0),
    experiencia VARCHAR(100),
    modalidad ENUM('VIRTUAL', 'PRESENCIAL', 'AMBAS') DEFAULT 'VIRTUAL',
    foto MEDIUMBLOB,
    foto_tipo VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_materia) REFERENCES materia(id) ON DELETE RESTRICT
);

CREATE TABLE recurso_biblioteca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    tipo_recurso VARCHAR(50) NOT NULL,
    url_archivo VARCHAR(255),
    descripcion TEXT,
    FOREIGN KEY (id_categoria) REFERENCES categoria_recurso(id) ON DELETE CASCADE
);

CREATE TABLE horario_tutor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_perfil_tutor INT NOT NULL,
    dia_semana ENUM('LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO') NOT NULL,
    hora_inicio TIME NOT NULL,
    cupos INT NOT NULL DEFAULT 1,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_perfil_tutor) REFERENCES perfil_tutor(id) ON DELETE CASCADE
);

CREATE TABLE tutorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_perfil_tutor INT NOT NULL,
    id_materia INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    duracion_minutos INT DEFAULT 60,
    cupos INT NOT NULL DEFAULT 1,
    precio DECIMAL(10, 0) NOT NULL,
    modalidad ENUM('VIRTUAL', 'PRESENCIAL') NOT NULL,
    foto MEDIUMBLOB,
    foto_tipo VARCHAR(50),
    FOREIGN KEY (id_perfil_tutor) REFERENCES perfil_tutor(id) ON DELETE CASCADE,
    FOREIGN KEY (id_materia) REFERENCES materia(id) ON DELETE RESTRICT
);

CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_tutoria INT NOT NULL,
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('CONFIRMADA', 'CANCELADA', 'COMPLETADA') DEFAULT 'CONFIRMADA',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_tutoria) REFERENCES tutorias(id) ON DELETE CASCADE
);

CREATE TABLE favorito (
    id_usuario INT NOT NULL,
    id_recurso INT NOT NULL,
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_recurso),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_recurso) REFERENCES recurso_biblioteca(id) ON DELETE CASCADE
);

INSERT INTO usuario (nombre, apellido, email, tipo_usuario, contrasena, telefono, fecha_registro) VALUES
('Ana', 'Gómez', 'ana.gomez@email.com', 'ADMIN_TUTOR', 'hashed_pass_1', '3001234567', '2026-01-15 08:30:00'),
('Carlos', 'Pérez', 'carlos.perez@email.com', 'ADMIN_TUTOR', 'hashed_pass_2', '3007654321', '2026-02-10 10:15:00'),
('Sofía', 'Rodríguez', 'sofia.rodriguez@email.com', 'ADMIN_TUTOR', 'hashed_pass_3', '3009876543', '2026-02-18 14:20:00'),
('Luis', 'Martínez', 'luis.martinez@email.com', 'ESTUDIANTE', 'hashed_pass_4', '3004561230', '2026-03-05 16:45:00'),
('Elena', 'Torres', 'elena.torres@email.com', 'ESTUDIANTE', 'hashed_pass_5', '3007894561', '2026-03-12 11:00:00');

INSERT INTO materia (nombre, area) VALUES
('Matemáticas Discretas', 'Ciencias Exactas'),
('Programación Orientada a Objetos', 'Tecnología'),
('Bases de Datos I', 'Tecnología'),
('Física Mecánica', 'Ciencias Exactas'),
('Inglés Técnico', 'Idiomas');

INSERT INTO categoria_recurso (nombre) VALUES
('Ebooks y Guías'),
('Ejercicios Prácticos'),
('Artículos de Investigación'),
('Presentaciones y Diapositivas'),
('Plantillas y Código Base');

-- Las imágenes reales se cargan desde la aplicación; no se insertan nombres de archivo como si fueran BLOB.
INSERT INTO perfil_tutor (id_usuario, id_materia, nivel, precio_base, experiencia, modalidad) VALUES
(1, 2, 'Avanzado', 50000, '3 años enseñando programación', 'VIRTUAL'),
(2, 1, 'Intermedio', 40000, '2 años de experiencia en matemáticas', 'AMBAS'),
(3, 3, 'Avanzado', 55000, '4 años como DBA y docente', 'VIRTUAL');

INSERT INTO recurso_biblioteca (id_categoria, titulo, tipo_recurso, url_archivo, descripcion) VALUES
(1, 'Guía definitiva de POO', 'PDF', 'https://recursos.com/poo-guia', 'Manual completo sobre pilares de la programación orientada a objetos.'),
(2, 'Ejercicios resueltos de Lógica', 'PDF', 'https://recursos.com/logica-ejercicios', 'Banco de problemas de matemáticas discretas con solución paso a paso.'),
(1, 'Introducción a Bases de Datos', 'EBOOK', 'https://recursos.com/db-intro', 'Conceptos clave de modelado relacional y SQL.'),
(3, 'El futuro de la IA en la educación', 'ARTICULO', 'https://recursos.com/ia-educacion', 'Artículo académico sobre tendencias tecnológicas.'),
(5, 'Plantilla SQL para Proyectos', 'ZIP', 'https://recursos.com/sql-template', 'Esquema base listo para importar en MySQL Workbench.');

INSERT INTO horario_tutor (id_perfil_tutor, dia_semana, hora_inicio, cupos, activo) VALUES
(1, 'LUNES', '10:00:00', 5, 1),
(1, 'MIERCOLES', '14:00:00', 5, 1),
(2, 'MARTES', '09:00:00', 10, 1),
(2, 'JUEVES', '09:00:00', 10, 1),
(3, 'VIERNES', '15:00:00', 6, 1);

INSERT INTO tutorias (id_perfil_tutor, id_materia, titulo, fecha, hora_inicio, duracion_minutos, cupos, precio, modalidad) VALUES
(1, 2, 'Dominando Clases y Objetos en Java', '2026-11-20', '10:00:00', 60, 5, 50000, 'VIRTUAL'),
(2, 1, 'Lógica Proposicional desde Cero', '2026-11-21', '09:00:00', 90, 10, 40000, 'PRESENCIAL'),
(3, 3, 'Normalización y Modelado ER', '2026-11-22', '15:00:00', 60, 6, 55000, 'VIRTUAL'),
(1, 2, 'Estructuras de Datos con Listas', '2026-11-23', '11:00:00', 60, 5, 50000, 'VIRTUAL'),
(2, 1, 'Teoría de Conjuntos Aplicada', '2026-11-24', '16:00:00', 60, 8, 40000, 'PRESENCIAL');

INSERT INTO reserva (id_usuario, id_tutoria, estado) VALUES
(4, 1, 'CONFIRMADA'),
(5, 1, 'CONFIRMADA'),
(4, 2, 'CONFIRMADA'),
(5, 3, 'COMPLETADA'),
(4, 5, 'CONFIRMADA');

INSERT INTO favorito (id_usuario, id_recurso) VALUES
(4, 1), (4, 3), (5, 2), (5, 1), (5, 5);

SELECT id, nombre, apellido, foto IS NOT NULL AS tiene_foto FROM usuario;
SELECT id, id_usuario, foto IS NOT NULL AS tiene_foto FROM perfil_tutor;
SELECT id, titulo, foto IS NOT NULL AS tiene_foto FROM tutorias;
