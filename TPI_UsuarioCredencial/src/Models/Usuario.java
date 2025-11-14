
package Models;

import java.time.LocalDateTime;

/**
 *
 * @author USER
 */
public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
    private CredencialAcceso credencial; //Relación unidireccional

    //Constructor
    public Usuario(String nombre, String apellido, String username, String email, Boolean activo, LocalDateTime fechaRegistro, Long id, Boolean eliminado) {
        super(id, false);
        this.nombre =  nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

    //Constructor vacío
    
    public Usuario() {
        super();
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    //Getters y Setters
    public void setApellido(String apellido) {    
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public CredencialAcceso getCredencial() {
        return credencial;
    }

    public void setCredencial(CredencialAcceso credencial) {
        this.credencial = credencial;
    }

    //Método toString
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
