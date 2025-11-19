# Historias de Usuario - Sistema de Gestión de Usuarios y Credenciales

Especificaciones funcionales completas del sistema CRUD de usuarios y credenciales de acceso.

## Tabla de Contenidos
- Épica 1: Gestión de Usuarios
- Épica 2: Gestión de Credenciales
- Épica 3: Operaciones de Seguridad
- Reglas de Negocio
- Modelo de Datos
- Flujos Técnicos Críticos
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

## HU-003: Buscar Usuario por Username
Como administrador del sistema  
Quiero buscar usuarios por username  
Para encontrar rápidamente usuarios específicos  

### Criterios de Aceptación (Gherkin)
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
- RN-010: Busca por username  
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

---

# Reglas de Negocio

- RN‑017: Eliminar usuario no borra credenciales
- RN‑018: Email debe ser formato válido
- RN‑019: Username no puede contener espacios
- RN‑020: Credenciales deben asociarse 1:1 a usuario
- RN‑021: Email se almacena normalizado
- RN‑022: La BD debe garantizar integridad 1:1
- RN‑023: Todos los campos deben validarse antes de insertar
- RN‑024: No se puede realizar UPDATE sin validar integridad

---

# Modelo de Datos Detallado (actualizado)

Usuario (1) — (1) Credencial

Tabla usuario:
- id (PK) — BIGINT AUTO_INCREMENT
- eliminado — BOOLEAN DEFAULT FALSE  (soft delete)
- nombre — VARCHAR(50) NOT NULL  
  - Validación: sólo letras y espacios (CHECK + TRIGGERS en la BD)
- apellido — VARCHAR(50) NOT NULL  
  - Validación: sólo letras y espacios (CHECK + TRIGGERS)
- username — VARCHAR(30) NOT NULL UNIQUE
- email — VARCHAR(120) NOT NULL UNIQUE
- activo — BOOLEAN NOT NULL DEFAULT TRUE
- fechaRegistro — DATETIME DEFAULT CURRENT_TIMESTAMP
- (nota: no hay columna fecha_actualizacion en el script; usar triggers o aplicación si se necesita)

Tabla credencial:
- id (PK) — BIGINT AUTO_INCREMENT
- eliminado — BOOLEAN DEFAULT FALSE
- contraseña — VARCHAR(255) NOT NULL  (almacenar hash)
- salt — VARCHAR(64)  (nullable)
- ultimo_cambio — DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  - funciona como fecha_creacion / fecha_modificacion
- require_reset — BOOLEAN NOT NULL DEFAULT FALSE
- id_usuario — BIGINT NOT NULL UNIQUE  (FK hacia usuario.id)
  - FOREIGN KEY ... REFERENCES usuario(id) ON DELETE CASCADE

---
# Flujos Técnicos Críticos

### 1. Crear usuario + credencial
1. Insertar usuario  
2. Insertar credencial con HASH + SALT 

### 2. Eliminación segura
- marcar eliminado = TRUE  
- invalidar credencial  

---

# Documentación Adicional
- README.md
---