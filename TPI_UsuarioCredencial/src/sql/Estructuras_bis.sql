create database usuariocredencial;
use usuariocredencial;

-- Eliminamos las tablas si ya existen con anterioridad para evitar inconsistencias.
DROP TABLE IF EXISTS Credencial;
DROP TABLE IF EXISTS Usuario;

-- creación de tabla usuario
create table usuario(
	
	id BIGINT PRIMARY KEY AUTO_INCREMENT, -- BIGINT para que coincida con LONG de Java
	eliminado BOOLEAN DEFAULT FALSE, -- por defecto no está eliminado
    
    -- información personal de usuario
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
	username VARCHAR(30) NOT NULL UNIQUE,
	email VARCHAR(120) NOT NULL UNIQUE,
    
    -- se elimino fecha de_nacimiento ya que el CHECK (fecha_nacimiento < CURRENT TIME) generaba un error
    
	activo BOOLEAN NOT NULL DEFAULT TRUE, -- por defecto está activo
    -- la fecha y hora se establece automáticamente al crear el usuario
	fechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP ,
    
    -- Restricción CHECK para que el nombre y apellido solo contengan letras (incluye acentos, ñ, y espacios) 
	-- En el caso de la documentación de las capturas se utilizaron TRIGGERS para validar esta restricción. 
	-- El Script de los TRIGGERS se encuentra al final de este documento
	CONSTRAINT chk_nombre_solo_letras 
	CHECK (nombre REGEXP '^[A-Za-zñÑáéíóúÁÉÍÓÚ\s]+'), 
	CONSTRAINT chk_apellido_solo_letras 
	CHECK (apellido REGEXP '^[A-Za-zñÑáéíóúÁÉÍÓÚ\s]+')

);

-- creación de tabla credencial
create table credencial(

	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	eliminado BOOLEAN DEFAULT FALSE,
	contraseña VARCHAR(255) NOT NULL,
    
    -- El salt es un dato público (se guarda junto al hash) que hace que cada contraseña encriptada sea única, 
    -- incluso si las contraseñas originales eran iguales. Es una medida de seguridad fundamental. 🔐
    salt VARCHAR(64),
    
    ultimo_cambio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- se actualiza automáticamente al modificarse
    require_reset BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- clave foránea
    id_usuario BIGINT NOT NULL UNIQUE, -- debe ser del mismo tipo que id en tabla usuario (BIGINT)

	-- Definición de la CLAVE FORÁNEA (FK)
    CONSTRAINT fk_credencial_usuario
        FOREIGN KEY (id_usuario) 
        REFERENCES usuario(id)
        ON DELETE CASCADE -- Si el usuario se elimina, su credencial también.
);


-- TRIGGERS
-- Deben ejecutarse y crearse antes de la inserción de datos. Por eso decide dejarse en la creación de tablas.

-- Medida de seguridad. Funciona como un reset en que caso que la sesión haya quedado atascado.
-- Esto y las líneas de abajo se implementan en caso de ejecutar parcialmente un bloque, que fue lo que nos pasó.
DELIMITER ;

-- 1. Se borran TRIGGERS anteriores en caso de error.
DROP TRIGGER IF EXISTS trg_usuario_check_letras_INSERT;
DROP TRIGGER IF EXISTS trg_usuario_check_letras_UPDATE;

-- 2. Cambiamos el delimitador para crear los nuevos
DELIMITER //

-- CREACIÓN DEL TRIGGER DE INSERT 
CREATE TRIGGER trg_usuario_check_letras_INSERT
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
    -- Revisamos si el NUEVO nombre NO CUMPLE la regla 
    IF (NEW.nombre NOT REGEXP '^[A-Za-zñÑáéíóúÁÉÍÓÚ\s]+$') THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error (Trigger INSERT): El nombre solo puede contener letras y espacios.';
    END IF;

    -- Revisamos si el NUEVO apellido NO CUMPLE la regla 
    IF (NEW.apellido NOT REGEXP '^[A-Za-zñÑáéíóúÁÉÍÓÚ\s]+$') THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error (Trigger INSERT): El apellido solo puede contener letras y espacios.';
    END IF;
END;
//

-- CREACIÓN DEL TRIGGER DE UPDATE
CREATE TRIGGER trg_usuario_check_letras_UPDATE
BEFORE UPDATE ON usuario
FOR EACH ROW
BEGIN
    IF (NEW.nombre NOT REGEXP '^[A-Za-zñÑáéíóúÁÉÍÓÚ\s]+$') THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error (Trigger UPDATE): El nombre solo puede contener letras y espacios.';
    END IF;

    IF (NEW.apellido NOT REGEXP '^[A-Za-zñÑáéíóúÁÉÍÓÚ\s]+$') THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error (Trigger UPDATE): El apellido solo puede contener letras y espacios.';
    END IF;
END;
//

-- 3. Volvemos el delimitador a la normalidad
DELIMITER ;
