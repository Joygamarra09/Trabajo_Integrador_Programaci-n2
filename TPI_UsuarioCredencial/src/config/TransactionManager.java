// Gestiona la atomicidad de las operaciones de la base de datos. Su único trabajo es hacer cumplir la regla de "Todo o Nada".
// En este proyecto, se usará para operaciones como crear un Usuario y su Credencial al mismo tiempo.



package Config; 

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Gestiona el ciclo de vida de una transacción (start, commit, rollback).
 * Implementa AutoCloseable para ser usado en bloques 'try-with-resources'.
 *
 * Esto garantiza que la conexión se cierre, se restaure el auto-commit,
 * y se haga rollback si la transacción quedó activa por un error.
 *
 * @author
 */
public class TransactionManager implements AutoCloseable {
    
    // Almacena el enlace activo que establece la comunicación con la BD ques esta clase supervisa.
    private Connection conn; // Es el atributo más importante de la clase. 
    private boolean transactionActive;

    /**
     * Constructor que recibe la conexión sobre la cual se gestionará la transacción.
     *
     * @param conn La conexión JDBC (no debe ser null).
     * @throws SQLException Si la conexión es null o está cerrada.
     */
    public TransactionManager(Connection conn) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        if (conn.isClosed()) {
             throw new SQLException("La conexión ya está cerrada, no se puede gestionar la transacción");
        }
        this.conn = conn;
        this.transactionActive = false; // La transacción no inicia hasta que se llama a startTransaction()
    }

    /**
     * Devuelve la conexión gestionada.
     * Es usado por la capa de Servicio para pasarla a los métodos ...Tx de los DAOs.
     *
     * @return La conexión JDBC.
     */
    public Connection getConnection() {
        return conn;
    }

    
    
    // MÉTODOS QUE UTILIZAN EL ATRIBUTO Connection conn;
    /**         Primero:
     * Inicia la transacción desactivando el auto-commit.
     * Pone la BD en "modo borrador".
     *
     * @throws SQLException Si la conexión está cerrada o falla el setAutoCommit.
     */
    public void startTransaction() throws SQLException {
        if (conn == null || conn.isClosed()) { //Si conn es nula o esta cerrada, lanza un throw exception.
            throw new SQLException("No se puede iniciar la transacción: conexión no disponible o cerrada");
        }
        
        // Si no, se desactiva el modo auto guardado para usarse de borrador.
        conn.setAutoCommit(false); // <--- Punto clave: desactiva el modo "auto-guardado"
        transactionActive = true;
    }

    /**         Segundo:
     * Confirma (hace commit) todos los cambios hechos en la transacción.
     * Le da el "sello" final a las operaciones.
     *
     * @throws SQLException Si no hay transacción activa o falla el commit.
     */
    public void commit() throws SQLException {
        if (conn == null) {
            throw new SQLException("Error al hacer commit: no hay conexión establecida");
        }
        if (!transactionActive) {
            // Previene hacer commit si no se inició una transacción
            throw new SQLException("No hay una transacción activa para hacer commit");
        }
        conn.commit(); // <-- Hace permanentes todos los cambios desde startTransaction()
        transactionActive = false; // <-- Indica a la clase que la transacción terminó exitosamente.
    }

    /**         Tercero:
     * Deshace (hace rollback) todos los cambios hechos en la transacción.
     * "Rompe el borrador" en caso de error.
     */
    public void rollback() {
        if (conn != null && transactionActive) {  // Si la conexión no es nula y transactionActive es tru
            try { // Intenta:
                conn.rollback(); // <-- Deshace todos los cambios desde startTransaction()
                transactionActive = false;
            } catch (SQLException e) {
                // Loguea el error, pero no lanza una excepción
                // (porque esto usualmente se llama DESPUÉS de que ya ocurrió otra excepción)
                System.err.println("Error durante el rollback: " + e.getMessage());
            }
        }
    }

    
    /**     Finalmente:
     * Método 'close' de la interfaz AutoCloseable.
     * Se llama AUTOMÁTICAMENTE al salir de un bloque try-with-resources.
     *
     * Lógica de seguridad:
     * 1. Si la transacción sigue activa (se olvidaron de hacer commit), hace rollback.
     * 2. Restaura el modo auto-commit (buena práctica).
     * 3. Cierra la conexión.
     */
    @Override
    public void close() {
        if (conn != null) {
            try {
                if (transactionActive) {
                    // Red de seguridad: Si se sale del 'try' (por una excepción)
                    // y la transacción seguía activa, se deshace todo.
                    System.err.println("ADVERTENCIA: Transacción cerrada sin commit. Haciendo rollback...");
                    rollback();
                }
                // Siempre restaurar el estado original de la conexión y cerrarla
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica si la transacción está actualmente activa.
     *
     * @return true si la transacción está activa, false en caso contrario.
     */
    public boolean isTransactionActive() {
        return transactionActive;
    }
}
