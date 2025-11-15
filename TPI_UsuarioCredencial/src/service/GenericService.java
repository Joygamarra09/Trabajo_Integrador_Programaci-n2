package service;

import config.ServiceException;
import java.util.List;

/**
 * Interfaz genérica para la capa de Servicio.
 * @param <T> El tipo de entidad.
 * @param <ID> El tipo de la clave primaria.
 */
public interface GenericService<T, ID> {

    // Cada operación abre y cierra su propia conexión/transacción si no son compuestas.
    // Se agrega manejo de errores de negocio (ServiceException).
    T save(T entity) throws ServiceException;
    void update(T entity) throws ServiceException;
    void delete(ID id) throws ServiceException;
    T getById(ID id) throws ServiceException;
    List<T> getAll() throws ServiceException;
}