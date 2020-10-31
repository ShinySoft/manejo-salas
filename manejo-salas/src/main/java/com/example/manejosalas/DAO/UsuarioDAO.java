package com.example.manejosalas.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.manejosalas.entidad.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

	Usuario findByCorreo(String correo);
}
