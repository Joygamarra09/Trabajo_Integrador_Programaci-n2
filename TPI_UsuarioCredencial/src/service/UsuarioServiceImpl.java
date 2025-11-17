package service;

import dao.CredencialAccesoDAO;
import dao.UsuarioDAO;
import Models.Usuario;
import Models.CredencialAcceso;
import config.DatabaseConnection;
import config.ServiceException;
import Config.TransactionManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de negocio para la entidad Usuario.
 * Gestiona operaciones atómicas con transacciones para crear usuario y credencial.
 */
public class UsuarioServiceImpl implements GenericService<Usuario, Long> {

    private final UsuarioDAO usuarioDAO;
    private final CredencialAccesoDAO credencialDAO;

    public UsuarioServiceImpl(UsuarioDAO usuarioDAO, CredencialAccesoDAO credencialDAO) {
        if (usuarioDAO == null) {
            throw new IllegalArgumentException("UsuarioDAO no puede ser null");
        }
        if (credencialDAO == null) {
            throw new IllegalArgumentException("CredencialAccesoDAO no puede ser null");
        }
        this.usuarioDAO = usuarioDAO;
        this.credencialDAO = credencialDAO;
    }

    /**
     * Valida que un usuario tenga datos correctos.
     */
    private void validateUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }

        // Validaciones básicas
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio");
        }
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }

        // Validación de formato de email
        if (usuario.getEmail() == null || !usuario.getEmail().matches("^[^@]+@[^@]+\\.[^@]+$")) {
            throw new IllegalArgumentException("El email es obligatorio y debe tener formato válido (ej: usuario@dominio.com)");
        }

        // Validación de longitud
        if (usuario.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede exceder 100 caracteres");
        }
        if (usuario.getUsername().length() > 50) {
            throw new IllegalArgumentException("El username no puede exceder 50 caracteres");
        }
        if (usuario.getEmail().length() > 255) {
            throw new IllegalArgumentException("El email no puede exceder 255 caracteres");
        }
    }
    
    /**
     * Valida que una credencial tenga datos correctos.
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
    }

    /**
     * OPERACIÓN TRANSACCIONAL COMPUESTA
     * Crea un usuario junto con su credencial en una transacción atómica.
     * 
     * Flujo transaccional:
     * 1. Abrir transacción: setAutoCommit(false)
     * 2. Ejecutar operaciones compuestas (crear usuario, luego credencial)
     * 3. Commit si todo OK, Rollback ante cualquier error
     * 4. Restablecer autoCommit(true) y cerrar recursos automáticamente
     */
    public Usuario crearUsuarioConCredencial(Usuario usuario, CredencialAcceso credencial) throws ServiceException {
        validateUsuario(usuario);
        validateCredencial(credencial);

        // Verificar que el username no exista
        try {
            Usuario usuarioExistente = usuarioDAO.getByUsername(usuario.getUsername());
            if (usuarioExistente != null) {
                throw new ServiceException("El username '" + usuario.getUsername() + "' ya está en uso");
            }
        } catch (Exception e) {
            throw new ServiceException("Error al verificar disponibilidad del username: " + e.getMessage(), e);
        }

        try (TransactionManager txManager = new TransactionManager(DatabaseConnection.getConnection())) {
            txManager.startTransaction();

            // Insertar usuario primero
            usuarioDAO.insertTx(usuario, txManager.getConnection());

            // Validar regla 1→1: impedir más de una credencial por usuario
            CredencialAcceso credencialExistente = credencialDAO.getByIdUsuario((int) usuario.getId());
            if (credencialExistente != null) {
                throw new ServiceException("Ya existe una credencial para este usuario (ID: " + usuario.getId() + ")");
            }

            // Asociar credencial al usuario recién creado - SIN CONVERSIÓN
            credencial.setUsuarioId(usuario.getId()); // getId() ya es long
            if (credencial.getUltimoCambio() == null) {
                credencial.setUltimoCambio(LocalDateTime.now());
            }

            // Insertar credencial
            credencialDAO.insertTx(credencial, txManager.getConnection());

            txManager.commit();
            
            // Asignar la credencial al usuario para retornarlo completo
            usuario.setCredencial(credencial);
            return usuario;

        } catch (Exception e) {
            throw new ServiceException("Error al crear usuario y credencial: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario save(Usuario entity) throws ServiceException {
        validateUsuario(entity);
        
        try {
            // Verificar que el username no exista
            Usuario usuarioExistente = usuarioDAO.getByUsername(entity.getUsername());
            if (usuarioExistente != null) {
                throw new ServiceException("El username '" + entity.getUsername() + "' ya está en uso");
            }
            
            usuarioDAO.insertar(entity);
            return entity;
        } catch (Exception e) {
            throw new ServiceException("Error al guardar Usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Usuario entity) throws ServiceException {
        validateUsuario(entity);
        
        // getId() devuelve long primitivo, NO puede ser null
        if (entity.getId() <= 0) {
            throw new ServiceException("El ID del usuario debe ser mayor a 0 para actualizar");
        }
        
        try {
            // Verificar que el usuario existe
            Usuario usuarioExistente = usuarioDAO.getById((int) entity.getId());
            if (usuarioExistente == null) {
                throw new ServiceException("No se encontró el usuario con ID: " + entity.getId());
            }
            
            usuarioDAO.actualizar(entity);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar Usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("El ID debe ser mayor a 0");
        }
        
        try {
            // Verificar que el usuario existe
            Usuario usuarioExistente = usuarioDAO.getById(id.intValue());
            if (usuarioExistente == null) {
                throw new ServiceException("No se encontró el usuario con ID: " + id);
            }
            
            usuarioDAO.eliminar(id.intValue());
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar Usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario getById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("El ID debe ser mayor a 0");
        }
        try {
            return usuarioDAO.getById(id.intValue());
        } catch (Exception e) {
            throw new ServiceException("Error al obtener Usuario por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> getAll() throws ServiceException {
        try {
            return usuarioDAO.getAll();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener todos los Usuarios: " + e.getMessage(), e);
        }
    }

    /**
     * Método adicional para obtener usuario por username.
     */
    public Usuario getByUsername(String username) throws ServiceException {
        if (username == null || username.trim().isEmpty()) {
            throw new ServiceException("El username no puede estar vacío");
        }
        try {
            return usuarioDAO.getByUsername(username);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener Usuario por username: " + e.getMessage(), e);
        }
    }
}