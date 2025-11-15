package config;

// Excepción personalizada para errores de negocio y validaciones en la capa Service.
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}