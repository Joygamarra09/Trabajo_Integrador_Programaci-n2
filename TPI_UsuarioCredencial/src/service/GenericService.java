package service;

import config.ServiceException;
import java.util.List;

/**
 * Interfaz genérica para servicios de negocio.
 * Define operaciones CRUD básicas con manejo de excepciones de negocio.
 *
 * @param <T> Tipo de entidad
 * @param <ID> Tipo del ID de la entidad
 */
public interface GenericService<T, ID> {
    
    /**
     * Guarda una nueva entidad en el sistema.
     * 
     * @param entity Entidad a guardar
     * @return Entidad guardada con ID asignado
     * @throws ServiceException Si la validación falla o hay error de negocio
     */
    T save(T entity) throws ServiceException;
    
    /**
     * Actualiza una entidad existente.
     * 
     * @param entity Entidad con datos actualizados
     * @throws ServiceException Si la entidad no existe o hay error de negocio
     */
    void update(T entity) throws ServiceException;
    
    /**
     * Elimina una entidad del sistema.
     * 
     * @param id ID de la entidad a eliminar
     * @throws ServiceException Si la entidad no existe o hay error de negocio
     */
    void delete(ID id) throws ServiceException;
    
    /**
     * Obtiene una entidad por su ID.
     * 
     * @param id ID de la entidad a buscar
     * @return Entidad encontrada o null si no existe
     * @throws ServiceException Si hay error de negocio
     */
    T getById(ID id) throws ServiceException;
    
    /**
     * Obtiene todas las entidades del sistema.
     * 
     * @return Lista de entidades (puede estar vacía)
     * @throws ServiceException Si hay error de negocio
     */
    List<T> getAll() throws ServiceException;
}