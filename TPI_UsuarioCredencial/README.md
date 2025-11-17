# Sistema de Gestión de Usuarios y Credenciales

## Trabajo Práctico Integrador - Programación 2

### Descripción del Proyecto

Este repositorio contiene un sistema de consola para la gestión de usuarios y credenciales desarrollado como Trabajo Práctico Integrador de la materia Programación 2. El objetivo es demostrar conceptos de Programación Orientada a Objetos, persistencia con JDBC y una arquitectura en capas (Modelo, DAO, Service, Main/UI).

### Objetivos Académicos

- **Arquitectura en Capas**: Separación clara en capas de Presentación, Servicio, DAO y Modelos.
- **POO y Patrones**: Uso de herencia, interfaces genéricas, Factory para conexión y Service/DAO patterns.
- **Persistencia con JDBC**: Operaciones CRUD con PreparedStatements, manejo de transacciones y soft delete.
- **Gestión de Recursos**: Try-with-resources y manejo de excepciones consistente.

### Características Principales

- **Gestión de Usuarios y Credenciales**: Operaciones básicas CRUD sobre las entidades del dominio.
- **Soft Delete**: Eliminación lógica mediante campo `eliminado` para preservar integridad referencial.
- **Validaciones**: Validaciones en capa de servicio y restricciones en base de datos (unicidad, no-null).
- **Protección contra SQL Injection**: Todas las consultas usan `PreparedStatement`.
- **Transaccionalidad**: Soporte para commit / rollback en operaciones críticas.

### Requisitos del Sistema

- **Java JDK**: 17 o superior
- **MySQL**: 8.0 o superior (u otra BD compatible con el driver usado)
- **Build**: Ant / NetBeans (el proyecto incluye `build.xml` y configuración NB)
- **Sistema Operativo**: Windows, Linux o macOS

### Instalación y Preparación de la Base de Datos

Los scripts SQL del proyecto se encuentran en `src/sql/` y `build/sql/`. Se puede crear la base de datos y tablas ejecutando los scripts allí provistos.

Ejemplo mínimo (ajustar nombres según script):

```sql
CREATE DATABASE IF NOT EXISTS usuariocredencial;
USE usuariocredencial;

```
### Configuración de Conexión

La configuración por defecto está en la clase `src/config/DatabaseConnection.java`.

- Valor por defecto en el repositorio: una URL y credenciales preconfiguradas (servicio Aiven).
- Recomendado: Editar `DatabaseConnection.java` con la contraseña ().

### Compilar y Ejecutar

Opción Recomendada — Desde IDE:
- Abrir el proyecto en NetBeans ejecutar la clase `main.Main`.

### Verificar Conexión

Usar la clase `main.TestConexion` para comprobar que la aplicación puede conectar a la base de datos. La salida esperada muestra información del usuario, base y driver.

### Estructura del Proyecto (resumen)

- `src/config/` : `DatabaseConnection.java`, `TransactionManager.java`, manejo de conexiones y transacciones
- `src/Models/` : Entidades del dominio (clases base, Usuario, Credencial, etc.)
- `src/dao/` : Implementaciones DAO y `GenericDAO`
- `src/service/` : Lógica de negocio y validaciones (`GenericService`, implementaciones)
- `src/main/` : Entrada y UI de consola (`Main.java`, `AppMenu.java`, `MenuHandler.java`, `TestConexion.java`)
- `src/sql/` y `build/sql/` : Scripts de creación e inserción de datos

### Patrones y Buenas Prácticas Aplicadas

- Factory para conexión (`DatabaseConnection`)
- DAO Pattern para abstracción del acceso a datos
- Service Layer para validaciones y reglas de negocio
- Try-with-resources y `AutoCloseable` para manejo de recursos
- Uso exclusivo de `PreparedStatement` para prevenir inyección SQL

### Troubleshooting (Problemas comunes)

- Error `ClassNotFoundException: com.mysql.cj.jdbc.Driver`: incluir el JAR del conector MySQL en el classpath
- Error `Communications link failure`: MySQL no está corriendo (iniciar servicio)
- Error `Access denied for user 'root'@'localhost'`: credenciales incorrectas; usar `-Ddb.user` y `-Ddb.password` o corregir `DatabaseConnection`
- Error `Unknown database 'usuariocredencial'`: ejecutar scripts SQL en `src/sql` para crear la BD

### Limitaciones Conocidas

- Interfaz únicamente por consola
- No se usa pool de conexiones (cada operación abre una conexión)
- Eliminación lógica (soft delete) — no hay borrado físico

---

**Versión**: 1.0
**Java**: 17+
**Proyecto educativo** — Trabajo Práctico Integrador de Programación 2
