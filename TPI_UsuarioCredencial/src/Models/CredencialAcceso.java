
package Models;

import java.time.LocalDateTime;

/**
 *
 * @author USER
 */
public class CredencialAcceso extends Base{
    private String hashPassword;
    private String salt;
    private LocalDateTime ultimoCambio;
    private Boolean requiereReset;
    private int usuarioId;

    //Constructor
    public CredencialAcceso(String hashPassword, String salt, LocalDateTime ultimoCambio, Boolean requiereReset,int usuarioID, Long id, Boolean eliminado) {
        super(id, false);
        this.hashPassword = hashPassword;
        this.salt = salt;
        this.ultimoCambio = ultimoCambio;
        this.requiereReset = requiereReset;
        this.usuarioId = usuarioID;
    }
    
    //Constructor vacío
    public CredencialAcceso() {
        super();
    }

    //Getters y Setters
    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getUltimoCambio() {
        return ultimoCambio;
    }

    public void setUltimoCambio(LocalDateTime ultimoCambio) {
        this.ultimoCambio = ultimoCambio;
    }

    public Boolean getRequiereReset() {
        return requiereReset;
    }

    public void setRequiereReset(Boolean requiereReset) {
        this.requiereReset = requiereReset;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    //Método toString
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
