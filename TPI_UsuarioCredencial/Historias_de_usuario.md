# Historias de Usuario - Sistema de Gestión de Usuarios y Credenciales

Especificaciones funcionales completas del sistema CRUD de usuarios y credenciales de acceso.

## Tabla de Contenidos
- Épica 1: Gestión de Usuarios
- Épica 2: Gestión de Credenciales
- Épica 3: Operaciones de Seguridad
- Reglas de Negocio
- Modelo de Datos
- Constraints y Validaciones SQL
- Triggers de Validación
- Queries Principales
- Flujos Técnicos Críticos
- Resumen de Operaciones del Sistema
- Documentación Relacionada

---

# Épica 1: Gestión de Usuarios

## HU-001: Crear Usuario
Como administrador del sistema  
Quiero crear un nuevo usuario con información personal  
Para registrar usuarios en el sistema  

### Criterios de Aceptación (Gherkin)

**Escenario: Crear usuario con datos válidos**  
Dado que el administrador selecciona "Crear usuario"  
Cuando ingresa nombre "Juan", apellido "Pérez", username "juanperez", email "juan@email.com"  
Entonces el sistema crea el usuario con ID autogenerado  
Y establece activo = TRUE por defecto  
Y muestra "Usuario creado exitosamente con ID: X"

**Escenario: Crear usuario con username duplicado**  
Dado que existe un usuario con username "maria"  
Cuando el administrador intenta crear usuario con username "maria"  
Entonces el sistema muestra "Ya existe un usuario con ese username"  
Y no crea el registro

**Escenario: Crear usuario con email duplicado**  
Dado que existe un usuario con email "test@email.com"  
Cuando el administrador intenta crear usuario con el mismo email  
Entonces el sistema muestra "Ya existe un usuario con ese email"  
Y no crea el registro

**Escenario: Validación de nombre con caracteres inválidos**  
Dado que el administrador ingresa nombre "Juan123"  
Cuando intenta crear el usuario  
Entonces el sistema muestra "El nombre solo puede contener letras y espacios"  
Y no crea el registro

### Reglas de Negocio Aplicables
- RN-001: Nombre, apellido, username y email son obligatorios  
- RN-002: Username y email deben ser únicos en el sistema  
- RN-003: Nombre y apellido solo pueden contener letras y espacios  
- RN-004: ID se genera automáticamente (BIGINT AUTO_INCREMENT)  
- RN-005: Por defecto activo = TRUE y eliminado = FALSE  
- RN-006: La fecha de registro se establece automáticamente

### Implementación Técnica
- **DAO:** UsuarioDAO.insertar()  
- **Servicio:** UsuarioServiceImpl.save()  
- **Validación:** Triggers MySQL + validación en servicio  
- **Constraints:** UNIQUE en username y email, CHECK constraints  

---

## HU-002: Listar Usuarios Activos
Como administrador del sistema  
Quiero ver un listado de todos los usuarios activos  
Para gestionar las cuentas de usuario  

### Criterios de Aceptación (Gherkin)

**Escenario: Listar usuarios activos**  
Dado que existen usuarios en el sistema  
Cuando el administrador selecciona "Listar usuarios"  
Entonces el sistema muestra solo usuarios con activo = TRUE y eliminado = FALSE  
Y muestra ID, nombre, apellido, username, email y estado  

**Escenario: No hay usuarios activos**  
Dado que no existen usuarios activos  
Cuando el administrador lista usuarios  
Entonces el sistema muestra "No se encontraron usuarios activos"

### Reglas de Negocio Aplicables
- RN-007: Solo se listan usuarios con activo = TRUE y eliminado = FALSE  
- RN-008: Se incluye información básica del perfil  

### Implementación Técnica
- **DAO:** UsuarioDAO.getAll()  
- **Query:**  
```sql
SELECT * FROM usuario WHERE activo = TRUE AND eliminado = FALSE;
```

---

## HU-003: Buscar Usuario por Email o Username
Como administrador del sistema  
Quiero buscar usuarios por email o username  
Para encontrar rápidamente usuarios específicos  

### Criterios de Aceptación (Gherkin)
**Escenario: Buscar usuario por email exacto**  
Dado que existe usuario con email "admin@system.com"  
Cuando el administrador busca por "admin@system.com"  
Entonces el sistema retorna el usuario correspondiente  

**Escenario: Buscar usuario por username**  
Dado que existe usuario con username "juanperez"  
Cuando el administrador busca por "juanperez"  
Entonces el sistema retorna el usuario correspondiente  

**Escenario: Búsqueda sin resultados**  
Dado que no existe usuario con email "inexistente@email.com"  
Cuando el administrador busca  
Entonces el sistema retorna null  

### Reglas de Negocio
- RN-009: La búsqueda es exacta (no LIKE)  
- RN-010: Busca por email OR username  
- RN-011: Solo retorna usuarios no eliminados  

### Implementación Técnica
- **DAO:** UsuarioDAO.getByEmail(), UsuarioDAO.getByUsername()  
- **Servicio:** UsuarioServiceImpl.getByCredencial()  

---

## HU‑004: Actualizar Información de Usuario
Como administrador del sistema  
Quiero actualizar datos del usuario  
Para mantener su información al día

### Criterios de Aceptación
**Escenario: Actualización válida**  
Dado un usuario existente  
Cuando el administrador actualiza nombre, apellido o email  
Entonces el sistema guarda los cambios exitosamente

**Escenario: Email duplicado**  
Dado que existe un usuario con email registrado  
Cuando intento actualizar otro usuario con ese mismo email  
Entonces el sistema muestra “Email ya registrado”

### Regla de Negocio
- RN‑013: No se permite cambiar el ID
- RN‑014: Email debe mantenerse único

---

## HU‑005: Desactivar Usuario (Soft Delete)
Como administrador  
Quiero desactivar usuarios  
Para que no puedan acceder sin perder su historial

### Criterios
- Cambiar `activo = FALSE`
- Mantener registro en BD

---

# Épica 2: Gestión de Credenciales

---

## HU‑006: Crear Credencial de Acceso
Incluye creación de contraseña encriptada con SALT único.

### Reglas
- RN‑015: Contraseña mínima 8 caracteres
- RN‑016: SALT debe generarse con algoritmo criptográfico seguro

---

## HU‑007: Obtener Credencial por Usuario
Retorna HASH, SALT y metadata de seguridad.

---

## HU‑008: Actualizar Credencial
Incluye rotación de contraseña y nuevo SALT.

---

## HU‑009: Eliminar Credencial
Eliminación segura con NULL de la contraseña.

---

# Épica 3: Operaciones de Seguridad

---

## HU‑010: Validar Credenciales
Comparación HASH + SALT.

## HU‑011: Forzar Cambio de Contraseña
Marcado de estado `requiere_cambio = TRUE`.

## HU‑012: Gestión de SALT
Reglas de renovación periódica del SALT.

---

# Reglas de Negocio (RN‑013 – RN‑050)

- RN‑017: Eliminar usuario no borra credenciales
- RN‑018: La contraseña nunca se almacena en texto plano
- RN‑019: SALT debe tener mínimo 32 bytes
- RN‑020: Intentos fallidos deben registrarse
- RN‑021: Bloqueo temporal tras 5 intentos fallidos
- RN‑022: Email debe ser formato válido RFC 5322
- RN‑023: Auditar cada cambio de credenciales
- RN‑024: Usuarios desactivados no pueden autenticarse
- RN‑025: Cada actualización debe registrar timestamp
- RN‑026: Toda operación requiere ID de administrador
- RN‑027: Username no puede contener espacios
- RN‑028: Username longitud entre 4 y 25 caracteres
- RN‑029: Contraseña debe incluir letras y números
- RN‑030: Credenciales deben asociarse 1:1 a usuario
- RN‑031: Eliminación de credenciales es reversible
- RN‑032: Username se almacena en minúsculas
- RN‑033: Email se almacena normalizado
- RN‑034: Fecha de modificación obligatoria
- RN‑035: HASH generado con PBKDF2 o bcrypt
- RN‑036: No se permiten contraseñas repetidas
- RN‑037: No se permite SALT repetido
- RN‑038: Credenciales expiran cada 90 días
- RN‑039: Los triggers no pueden alterar IDs
- RN‑040: La BD debe garantizar integridad 1:1
- RN‑041: No se permite eliminar administradores principales
- RN‑042: Auditoría no puede ser modificada
- RN‑043: Bloqueo automático del usuario tras intentos fallidos
- RN‑044: Todos los campos deben validarse antes de insertar
- RN‑045: Usuario eliminado no puede ser reactivado sin auditoría
- RN‑046: Hash debe incluir algoritmo + SALT concatenado
- RN‑047: El sistema registra IP del último inicio de sesión
- RN‑048: Todos los correos enviados deben registrarse
- RN‑049: No se puede realizar UPDATE sin validar integridad
- RN‑050: Todo cambio debe registrarse en tabla LOG

---

# Modelo de Datos Detallado

```
Usuario (1) — (1) Credencial

Tabla usuario:
- id_usuario (PK)
- nombre
- apellido
- username (UNIQUE)
- email (UNIQUE)
- activo
- eliminado
- fecha_registro
- fecha_actualizacion

Tabla credencial:
- id_credencial (PK)
- id_usuario (FK UNIQUE)
- hash_password
- salt
- fecha_creacion
- fecha_modificacion
- requiere_cambio
```

---

# SQL COMPLETO (DDL, Constraints, Triggers)

```
CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) CHECK(nombre REGEXP '^[A-Za-z ]+$'),
    apellido VARCHAR(50) CHECK(apellido REGEXP '^[A-Za-z ]+$'),
    username VARCHAR(25) UNIQUE NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    eliminado BOOLEAN DEFAULT FALSE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NULL
);

CREATE TABLE credencial (
    id_credencial BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL UNIQUE,
    hash_password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP NULL,
    requiere_cambio BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);
```

---

# Triggers

```
CREATE TRIGGER trg_usuario_update
BEFORE UPDATE ON usuario
FOR EACH ROW
SET NEW.fecha_actualizacion = CURRENT_TIMESTAMP;
```

```
CREATE TRIGGER trg_credencial_update
BEFORE UPDATE ON credencial
FOR EACH ROW
SET NEW.fecha_modificacion = CURRENT_TIMESTAMP;
```

---

# Flujos Técnicos Críticos

### 1. Crear usuario + credencial
1. Insertar usuario  
2. Insertar credencial con HASH + SALT  
3. Registrar auditoría  

### 2. Autenticación
1. Obtener usuario  
2. Obtener credencial  
3. Recalcular HASH  
4. Comparar  
5. Registrar auditoría  

### 3. Eliminación segura
- marcar eliminado = TRUE  
- invalidar credencial  

---

# Documentación Adicional
- README.md
---