
package Models;

/**
 * Clase base abstracta que proporciona atributos comunes a todas las entidades del modelo.
 * Incluye un identificador único y una marca de eliminación lógica.
 * Sirve como superclase para modelos como Usuario, CredencialAcceso, etc.
 * 
 * @author USER
 */
public abstract class Base {
    private long id; //Identificador único
    private boolean eliminado; //Marca en la base de datos cuando un elemento es eliminado de forma lógica

    /**
     * Constructor completo para inicializar los atributos base.
     *
     * @param id Identificador único.
     * @param eliminado Estado de eliminación lógica.
     */
    public Base(long id, boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }

    /**
     * Constructor vacío requerido por frameworks o para inicialización manual.
     */
    public Base() {
    }
    
    //Getters y Setters
    
    /**
     * Obtiene el identificador único de la entidad.
     * @return El ID de la entidad.
     */
    public long getId() {
        return id;
    }
    
    /**
     * Establece el identificador único de la entidad.
     * @param id Nuevo ID de la entidad.
     */
    public void setId(long id) {
        this.id = id;
    }

    
    /**
     * Obtiene el estado de eliminación lógica de la entidad.
     * @return true si la entidad ha sido eliminada lógicamente, false si no.
     */
    public boolean getEliminado(){
        return eliminado;
    }
    
    /**
     * Establece o cambia el estado de eliminación lógica de la entidad.
     * @param eliminado El nuevo estado de eliminación (true para eliminado, false para activo).
     */
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    /**
     * Indica si la entidad ha sido marcada para eliminación lógica.
     * @return true si la entidad está eliminada, false si no.
     */
    public boolean isEliminado(){
        return eliminado;
    }
    
   
    
    
}
