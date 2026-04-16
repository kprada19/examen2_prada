-- ================================================================
--  Script MySQL — examen2_prada
--  Estrategia ORM: JOINED TABLE (InheritanceType.JOINED)
-- ================================================================

CREATE DATABASE IF NOT EXISTS examen2_prada
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE examen2_prada;

-- ================================================================
-- TABLA 1 — asegurado
-- Clase: Asegurado
-- ================================================================
CREATE TABLE IF NOT EXISTS asegurado (
    -- @Id
    id      VARCHAR(20)  NOT NULL  COMMENT '@Id',
    -- @Column
    nombre  VARCHAR(100) NOT NULL  COMMENT '@Column',

    PRIMARY KEY (id)
) ENGINE = InnoDB 
  COMMENT = '@Entity Asegurado';

-- ================================================================
-- TABLA 2 — seguro
-- Clase: Seguro (Clase base / Abstracta)
-- ================================================================
CREATE TABLE IF NOT EXISTS seguro (
    -- @Id (PK compartida con subclases)
    numero      VARCHAR(50)  NOT NULL  COMMENT '@Id',
    
    -- @Column (formato fecha)
    fecha_exp   DATE         NOT NULL  COMMENT '@Column heredado',
    
    -- @Column (1=activo, 0=inactivo)
    estado      TINYINT(1)   NOT NULL  DEFAULT 1 COMMENT '@Column heredado',

    -- @ManyToOne (N seguros -> 1 asegurado)
    asegurado_id VARCHAR(20)  NOT NULL  COMMENT '@ManyToOne FK -> asegurado.id',

    PRIMARY KEY (numero),
    
    CONSTRAINT fk_seguro_asegurado
        FOREIGN KEY (asegurado_id)
        REFERENCES asegurado (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE = InnoDB 
  COMMENT = '@Entity Seguro — tabla base JOINED';

-- ================================================================
-- TABLA 3 — seguro_vehiculo
-- Clase: SeguroVehiculo extends Seguro
-- ================================================================
CREATE TABLE IF NOT EXISTS seguro_vehiculo (
    -- @PrimaryKeyJoinColumn (FK 1:1 -> seguro.numero)
    numero  VARCHAR(50) NOT NULL  COMMENT '@PrimaryKeyJoinColumn',
    
    -- @Column propio de la subclase
    marca   VARCHAR(50) NOT NULL  COMMENT '@Column propio Vehiculo',

    PRIMARY KEY (numero),

    CONSTRAINT fk_vehiculo_seguro
        FOREIGN KEY (numero)
        REFERENCES seguro (numero)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE = InnoDB 
  COMMENT = '@Entity SeguroVehiculo extends Seguro';

-- ================================================================
-- TABLA 4 — seguro_vida
-- Clase: SeguroVida extends Seguro
-- ================================================================
CREATE TABLE IF NOT EXISTS seguro_vida (
    -- @PrimaryKeyJoinColumn (FK 1:1 -> seguro.numero)
    numero       VARCHAR(50)  NOT NULL  COMMENT '@PrimaryKeyJoinColumn',
    
    -- @Column propio de la subclase
    beneficiario VARCHAR(100) NOT NULL  COMMENT '@Column propio Vida',

    PRIMARY KEY (numero),

    CONSTRAINT fk_vida_seguro
        FOREIGN KEY (numero)
        REFERENCES seguro (numero)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE = InnoDB 
  COMMENT = '@Entity SeguroVida extends Seguro';

-- ================================================================
-- Datos de prueba
-- ================================================================

INSERT INTO asegurado (id, nombre) VALUES 
    ('A001', 'Juan Prada'), 
    ('A002', 'Maria Lopez');

-- Insertamos primero en la tabla padre
INSERT INTO seguro (numero, fecha_exp, estado, asegurado_id) VALUES
    ('VEH-001', '2027-12-31', 1, 'A001'),
    ('VID-001', '2028-06-15', 1, 'A001'),
    ('VEH-002', '2025-09-20', 0, 'A002');

-- Luego en las hijas según corresponda
INSERT INTO seguro_vehiculo (numero, marca) VALUES
    ('VEH-001', 'Mazda'),
    ('VEH-002', 'Toyota');

INSERT INTO seguro_vida (numero, beneficiario) VALUES
    ('VID-001', 'Elena Prada');
