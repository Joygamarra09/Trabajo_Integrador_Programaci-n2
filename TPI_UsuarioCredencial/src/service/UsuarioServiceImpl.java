package service;
// Hola Gabi!!
import DAO.CredencialAccesoDAO;
import dao.UsuarioDAO;
import Models.Usuario;
import Models.CredencialAcceso;
import dao.DatabaseConnection;
import config.ServiceException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UsuarioServiceImpl implements GenericService<Usuario, Long> {

    private final UsuarioDAO<Usuario> usuarioDAO;
    private final CredencialAccesoDAO credencialDAO;

    public UsuarioServiceImpl(UsuarioDAO<Usuario> usuarioDAO, CredencialAccesoDAO credencialDAO) {
        this.usuarioDAO = usuarioDAO;
        this.credencialDAO = credencialDAO;
    }

    private void validateUsuario(Usuario u) throws ServiceException {
        if (u.getNombre() == null || u.getNombre().trim().isEmpty()) {
            throw new ServiceException("El nombre del usuario es obligatorio.");
        }
        if (u.getEmail() == null || !u.getEmail().contains("@")) {
            throw new ServiceException("El email es obligatorio y debe tener formato válido.");
        }
    }

    private void validateCredencial(CredencialAcceso c) throws ServiceException {
        if (c.getHashPassword() == null || c.getHashPassword().trim().length() < 8) {
            throw new ServiceException("El hash de la contraseña es obligatorio y debe tener al menos 8 caracteres.");
        }
    }

    public Usuario crearUsuarioConCredencial(Usuario usuario, CredencialAcceso credencial) throws ServiceException {
        Connection conn = null;
        try {
            validateUsuario(usuario);
            validateCredencial(credencial);

            conn = DatabaseConnection.getConnection();
            //DatabaseConnection.beginTransaction(conn);

            if (usuario.getFechaRegistro() == null) {
                usuario.setFechaRegistro(LocalDateTime.now());
            }

            usuarioDAO.insertTx(usuario, conn);

            // 5. Crear CredencialAcceso (necesitarías un método similar en CredencialAccesoDAO)
            // credencial.setUsuarioId(usuario.getId());
            // if (credencial.getUltimoCambio() == null) {
            //     credencial.setUltimoCambio(LocalDateTime.now());
            // }
            // credencialDAO.insertTx(credencial, conn);

            //DatabaseConnection.commitTransaction(conn);
            return usuario;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    DatabaseConnection.rollbackTransaction(conn);
                } catch (SQLException ex) {
                    throw new ServiceException("Error al hacer rollback", ex);
                }
            }
            throw new ServiceException("Error al crear usuario y credencial: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                DatabaseConnection.closeConnection(conn);
            }
        }
    }

    @Override
    public Usuario save(Usuario entity) throws ServiceException {
        validateUsuario(entity);
        try {
            usuarioDAO.insertar(entity); 
            return entity;
        } catch (Exception e) {
            throw new ServiceException("Error al guardar Usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Usuario entity) throws ServiceException {
        validateUsuario(entity);
        try {
            usuarioDAO.actualizar(entity); 
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar Usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            // Provisoriamente convertir long a int y usar eliminar
            usuarioDAO.eliminar(id.intValue());
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar Usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario getById(Long id) throws ServiceException {
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
}