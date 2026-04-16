-- Crear base de datos
CREATE DATABASE IF NOT EXISTS examen2_prada;
USE examen2_prada;

-- Tabla Asegurado
CREATE TABLE asegurado (
    id VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla Padre: Seguro
CREATE TABLE seguro (
    numero VARCHAR(50) PRIMARY KEY,
    fecha_exp DATE NOT NULL,
    estado BOOLEAN NOT NULL,
    asegurado_id VARCHAR(20),
    CONSTRAINT fk_asegurado FOREIGN KEY (asegurado_id) REFERENCES asegurado(id)
);

-- Tabla Hija: Seguro Vehiculo
CREATE TABLE seguro_vehiculo (
    numero VARCHAR(50) PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    CONSTRAINT fk_vehiculo_seguro FOREIGN KEY (numero) REFERENCES seguro(numero) ON DELETE CASCADE
);

-- Tabla Hija: Seguro Vida
CREATE TABLE seguro_vida (
    numero VARCHAR(50) PRIMARY KEY,
    beneficiario VARCHAR(100) NOT NULL,
    CONSTRAINT fk_vida_seguro FOREIGN KEY (numero) REFERENCES seguro(numero) ON DELETE CASCADE
);

-- Datos iniciales para que el ComboBox funcione de una vez
INSERT INTO asegurado (id, nombre) VALUES ('A001', 'Juan Prada');
INSERT INTO asegurado (id, nombre) VALUES ('A002', 'Maria Lopez');