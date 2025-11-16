package service;

import DAO.CredencialAccesoDAO;
import Models.CredencialAcceso;
import config.ServiceException;
import java.util.List;

/**
 * Servicio de negocio para la entidad CredencialAcceso.
 * Aplica reglas de negocio y validaciones específicas.
 */
public class CredencialAccesoServiceImpl implements GenericService<CredencialAcceso, Long> {
    
    private final CredencialAccesoDAO credencialDAO;

    public CredencialAccesoServiceImpl(CredencialAccesoDAO credencialDAO) {
        if (credencialDAO == null) {
            throw new IllegalArgumentException("CredencialAccesoDAO no puede ser null");
        }
        this.credencialDAO = credencialDAO;
    }

    /**
     * Valida una credencial de acceso.
     */
    private void validateCredencial(CredencialAcceso credencial) {
        if (credencial == null) {
            throw new IllegalArgumentException("La credencial no puede ser null");
        }
        if (credencial.getHashPassword() == null || credencial.getHashPassword().trim().length() < 8) {
            throw new IllegalArgumentException("El hash de la contraseña es obligatorio y debe tener al menos 8 caracteres");
        } 
        if (credencial.getSalt() == null || credencial.getSalt().trim().isEmpty()) {
            throw new IllegalArgumentException("El salt es obligatorio para la seguridad de la contraseña");
        }
        // getUsuarioId() devuelve long primitivo, NO puede ser null
        if (credencial.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("El ID de usuario asociado es obligatorio y debe ser mayor a 0");
        }
    }

    @Override
    public CredencialAcceso save(CredencialAcceso entity) throws ServiceException {
        validateCredencial(entity);
        try {
            // Verificar que no exista ya una credencial para este usuario (regla 1→1)
            // getUsuarioId() devuelve long primitivo, usar cast a int
            CredencialAcceso existente = credencialDAO.getByIdUsuario((int) entity.getUsuarioId());
            if (existente != null) {
                throw new ServiceException("Ya existe una credencial para el usuario con ID: " + entity.getUsuarioId());
            }
            
            credencialDAO.insertar(entity);
            return entity;
        } catch (Exception e) {
            throw new ServiceException("Error al guardar CredencialAcceso: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(CredencialAcceso entity) throws ServiceException {
        validateCredencial(entity);
        // getId() devuelve long primitivo, NO puede ser null
        if (entity.getId() <= 0) {
            throw new ServiceException("El ID de la credencial debe ser mayor a 0 para actualizar");
        }
        try {
            credencialDAO.actualizar(entity);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar CredencialAcceso: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("El ID debe ser mayor a 0");
        }
        try {
            credencialDAO.eliminar(id.intValue());
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar CredencialAcceso: " + e.getMessage(), e);
        }
    }

    @Override
    public CredencialAcceso getById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("El ID debe ser mayor a 0");
        }
        try {
            return credencialDAO.getById(id.intValue());
        } catch (Exception e) {
            throw new ServiceException("Error al obtener CredencialAcceso por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CredencialAcceso> getAll() throws ServiceException {
        try {
            return credencialDAO.getAll();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener todas las CredencialesAcceso: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene la credencial de acceso por ID de usuario.
     * @param usuarioId
     * @return 
     * @throws config.ServiceException 
     */
    public CredencialAcceso getByUsuarioId(Long usuarioId) throws ServiceException {
        if (usuarioId == null || usuarioId <= 0) {
            throw new ServiceException("El ID de usuario debe ser mayor a 0");
        }
        try {
            return credencialDAO.getByIdUsuario(usuarioId.intValue());
        } catch (Exception e) {
            throw new ServiceException("Error al obtener credencial por ID de usuario: " + e.getMessage(), e);
        }
    }
}