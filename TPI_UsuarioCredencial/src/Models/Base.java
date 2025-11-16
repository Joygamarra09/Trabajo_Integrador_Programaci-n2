
package Models;

/**
 *
 * @author USER
 */
public abstract class Base {
    private long id; //Identificador único
    private boolean eliminado; //Marca en la base de datos cuando un elemento es eliminado de forma lógica

    //Constructor
    public Base(long id, boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }

    //Constructor vacio
    public Base() {
    }
    
    //Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    //Método que retorna si un elemento fue eliminado o no
    public boolean isEliminado(){
        return eliminado;
    }
    
   
    
    
}
