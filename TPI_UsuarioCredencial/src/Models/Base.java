
package Models;

/**
 *
 * @author USER
 */
public abstract class Base {
    private Long id; //Identificador único
    private Boolean eliminado; //Marca en la base de datos cuando un elemento es eliminado de forma lógica

    //Constructor
    public Base(Long id, Boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }

    //Constructor vacio
    public Base() {
    }
    
    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    //Método que retorna si un elemento fue eliminado o no
    public Boolean isEliminado(){
        return eliminado;
    }
    
   
    
    
}
