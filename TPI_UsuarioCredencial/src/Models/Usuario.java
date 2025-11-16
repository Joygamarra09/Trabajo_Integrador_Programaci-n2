
package Models;

import java.time.LocalDateTime;

/**
* Representa un usuario del sistema.
* Contiene información personal, credenciales de acceso y estado de actividad.
* Hereda de la clase Base, que incluye atributos comunes como id y eliminado.
* 
* @author USER
*/
public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private boolean activo;
    private LocalDateTime fechaRegistro;
    private CredencialAcceso credencial; //Relación unidireccional

    
    /**
     * Constructor completo para inicializar un usuario con todos sus atributos.
     *
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param username Nombre de usuario único.
     * @param email Correo electrónico.
     * @param activo Estado de actividad.
     * @param fechaRegistro Fecha de registro.
     * @param id Identificador único (heredado de Base).
     * @param eliminado Estado de eliminación lógica (heredado de Base).
     */
    public Usuario(String nombre, String apellido, String username, String email, boolean activo, LocalDateTime fechaRegistro, long id, boolean eliminado) {
        super(id, false);
        this.nombre =  nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

   
    /**
     * Constructor vacío requerido por frameworks o para inicialización manual.
     */ 
    public Usuario() {
        super();
    }
    
    //Getters y Setters
    
    /** @return Nombre del usuario. */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Nuevo nombre del usuario. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return Apellido del usuario. */
    public String getApellido() {
        return apellido;
    }

    /** @param apellido Nuevo apellido del usuario. */
    public void setApellido(String apellido) {    
        this.apellido = apellido;
    }

    /** @return Nombre de usuario. */
    public String getUsername() {
        return username;
    }

    /** @param username Nuevo nombre de usuario. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return Correo electrónico del usuario. */
    public String getEmail() {
        return email;
    }

    /** @param email Nuevo correo electrónico. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return true si el usuario está activo, false si no. */
    public boolean isActivo() {
        return activo;
    }

    /** @param activo Nuevo estado de actividad del usuario. */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /** @return Fecha de registro del usuario. */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /** @param fechaRegistro Nueva fecha de registro. */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /** @return Credencial de acceso asociada al usuario. */
    public CredencialAcceso getCredencial() {
        return credencial;
    }

    /** @param credencial Nueva credencial de acceso. */
    public void setCredencial(CredencialAcceso credencial) {
        this.credencial = credencial;
    }

    //Método toString
    
    /**
     * Devuelve una representación textual del usuario.
     * @return Cadena con los valores de los atributos.
     */
    @Override
    public String toString() {
        return """
            Usuario{
            id: %s,
            Nombre: %s,
            Apellido: %s,
            Username: %s,
            Email: %s,
            Activo: %s,
            FechaRegistro: %s,
            Credencial: %s,
            Eliminado: %s
            }""".formatted(getId(),nombre, apellido, username, email, activo, fechaRegistro, credencial, isEliminado());
    }
    
    
    
}
