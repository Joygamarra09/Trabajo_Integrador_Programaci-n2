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

La configuración por defecto está en la clase `src/config/DatabaseConnection.java`.

- Valor por defecto en el repositorio: una URL y credenciales preconfiguradas (servicio Aiven).
- Recomendado: Editar `DatabaseConnection.java` e ingresar la contraseña proporcionada por Aiven en el campo correspondiente para poder conectarse a la base de datos remota.

Cómo conectarse a la base de datos remota a través de MySQL Workbench:
- El connection string JDBC en `DatabaseConnection.java` (ej.: jdbc:mysql://host:puerto/base?sslMode=REQUIRED&...) contiene todos los parámetros necesarios. Puede extraerlos y pegarlos en la creación de una nueva conexión en MySQL Workbench:
  - Hostname: host (parte después de // y antes de : )
  - Port: puerto (número después de : )
  - Username: el usuario (valor de db.user o el que aparezca en la URL)
  - Password: introducirla manualmente. Por motivos de seguridad, no se encuentra en el repositorio. Por favor, visualizarla en el siguiente link: [Pwd (Enlace a Drive)](https://docs.google.com/document/d/1PYLsZ7cfh4MEVmUacGWSpkS2A9tJkmE2olqPNE7H4e0/edit?usp=sharing)
  - Default Schema: nombre de la base de datos (parte después de /)
  - Use SSL / SSL Mode: según el parámetro sslMode (continuar en la siguiente sección para configurar el certificado en la pestaña SSL)

Subir el certificado CA en MySQL Workbench:
  1. Abrir MySQL Workbench → Database → Manage Connections → New (o Editar conexión existente).
  2. Ir a la pestaña "SSL".
  3. Ajustar "SSL Mode" a `REQUIRED` o `VERIFY_CA`/`VERIFY_IDENTITY` según lo indique el proveedor.
  4. En "SSL CA File" seleccionar el archivo `ca.pem`. Por motivos de seguridad, este archivo no se encuentra en el repositorio. Por favor, acceder en el siguiente link: [Certificado (Enlace a Drive)](https://drive.google.com/file/d/1kI4oK_duQLLjpDpyqhImZtUsfl62s-nl/view?usp=sharing)
  5. Guardar la conexión y probarla.

### Ejecutar scripts SQL (orden recomendado)

Los scripts SQL del proyecto están en `src/sql/`. Para evitar errores de dependencia entre tablas, ejecutar los archivos en este orden:

1. `Estructuras.sql` — crea la base de datos y las tablas.
2. `Insercion_datos.sql` — inserta datos de ejemplo.

### Verificar Conexión

Usar la clase `main.TestConexion` para comprobar que la aplicación puede conectar a la base de datos. La salida esperada muestra información del usuario, base y driver.

### Compilar y Ejecutar

Para acceder al menú interactivo, la opción recomendada es desde IDE:
- Abrir el proyecto en NetBeans y ejecutar la clase `main.Main`.

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

### Documentación adicional adjunta en este repositorio

- Reporte de casos de prueba manuales.pdf — Contiene los casos de prueba ejecutados manualmente y sus evidencias (capturas). Útil para reproducir pruebas y verificar regresiones.
- Historias_de_usuario.md — Describe las historias de usuario usadas para definir requisitos: resumen de cada historia, prioridad, criterios de aceptación y escenarios principales.

### Limitaciones Conocidas

- Interfaz únicamente por consola
- Eliminación lógica (soft delete) — no hay borrado físico

### Enlace al video

[Video de presentación del proyecto(Enlace a Drive)](https://drive.google.com/file/d/1sb5bSsXJBKqxY6oCMAl__sFzlg-XpowC/view?usp=sharing)

---

**Versión**: 1.0
**Java**: 17+
**Proyecto educativo** — Trabajo Práctico Integrador de Programación 2