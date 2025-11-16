-- ================================
-- CREACIÓN DE BASE DE DATOS
-- ================================
USE tpi_programacion2;


-- ================================
-- TABLA A: usuario
-- ================================
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    username VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(120) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ================================
-- TABLA B: credencial_acceso
-- ================================
CREATE TABLE credencial_acceso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    hash_password VARCHAR(255) NOT NULL,
    salt VARCHAR(64),
    ultimo_cambio DATETIME DEFAULT CURRENT_TIMESTAMP,
    requiere_reset BOOLEAN NOT NULL,

    -- Relación 1→1 unidireccional
    usuario_id BIGINT UNIQUE,
    CONSTRAINT fk_credencial_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);
