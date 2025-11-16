
package Models;

import java.time.LocalDateTime;

/**
 * Representa las credenciales de acceso asociadas a un usuario.
 * Contiene información sobre la contraseña encriptada, el salt,
 * la fecha del último cambio, si requiere reinicio y el ID del usuario.
 * Hereda de la clase Base, que incluye atributos comunes como id y eliminado.
 *
 * @author USER
 */
public class CredencialAcceso extends Base{
    private String hashPassword;
    private String salt;
    private LocalDateTime ultimoCambio;
    private boolean requiereReset;
    /**
     * Esto es la clave foránea que se usaría en una base de datos relacional para vincular la credencial de vuelta al usuario.
     */
    private long usuarioId;

    
    /**
     * Constructor completo para inicializar todas las propiedades de la credencial.
     *
     * @param hashPassword Contraseña encriptada.
     * @param salt Valor de salt para encriptación.
     * @param ultimoCambio Fecha del último cambio de contraseña.
     * @param requiereReset Si requiere reinicio de contraseña.
     * @param usuarioID ID del usuario asociado.
     * @param id ID de la credencial (heredado de Base).
     * @param eliminado Estado de eliminación lógica (heredado de Base).
     */
    public CredencialAcceso(String hashPassword, String salt, LocalDateTime ultimoCambio, boolean requiereReset,long usuarioID, long id, boolean eliminado) {
        super(id, false);
        this.hashPassword = hashPassword;
        this.salt = salt;
        this.ultimoCambio = ultimoCambio;
        this.requiereReset = requiereReset;
        this.usuarioId = usuarioID;
    }
    
    
    /**
     * Constructor vacío requerido por frameworks o para inicialización manual.
     */
    public CredencialAcceso() {
        super();
    }

    //Getters y Setters
    /**
     * Obtiene la contraseña encriptada.
     * @return hashPassword
     */
    public String getHashPassword() {
        return hashPassword;
    }

    /**
     * Establece la contraseña encriptada.
     * @param hashPassword Nueva contraseña encriptada (hash).
     */
    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    /**
     * Obtiene el valor de salt para encriptación.
     * @return El valor de salt.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Establece el valor de salt para la encriptación.
     * @param salt Nuevo valor de salt.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Obtiene la fecha del último cambio de contraseña.
     * @return ultimoCambio
     */
    public LocalDateTime getUltimoCambio() {
        return ultimoCambio;
    }

    /**
     * Establece la fecha del último cambio de contraseña.
     * @param ultimoCambio Nueva fecha de cambio.
     */
    public void setUltimoCambio(LocalDateTime ultimoCambio) {
        this.ultimoCambio = ultimoCambio;
    }
    
    /**
     * Indica si se requiere reinicio de contraseña.
     * @return true si requiere reinicio, false si no.
     */
    public boolean isRequiereReset() {
        return requiereReset;
    }

        /**
     * Establece si se requiere reinicio de contraseña.
     * @param requiereReset Nuevo estado de reinicio.
     */
    public void setRequiereReset(boolean requiereReset) {
        this.requiereReset = requiereReset;
    }

    /**
     * Obtiene el ID del usuario asociado.
     * @return usuarioId
     */
    public long getUsuarioId() {
        return usuarioId;
    }

     /**
     * Establece el ID del usuario asociado.
     * @param usuarioId Nuevo ID de usuario.
     */
    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Devuelve una representación textual de la credencial de acceso.
     * @return Cadena con los valores de los atributos.
     */
    @Override
    public String toString() {
        return """
            CredencialAcceso{
            id: %s,
            hashPassword: %s,
            salt: %s,
            ultimoCambio: %s,
            requiereReset: %s,
            usuarioID: %s
            eliminado: %s
            }""".formatted(getId(), hashPassword, salt, ultimoCambio, requiereReset,usuarioId, isEliminado());
    }
  
    
}
