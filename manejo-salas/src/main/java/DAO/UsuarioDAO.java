package DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import entidad.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {


}
