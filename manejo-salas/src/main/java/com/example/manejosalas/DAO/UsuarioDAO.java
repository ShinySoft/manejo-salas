package com.example.manejosalas.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.manejosalas.entidad.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

	Usuario findByCorreo(String correo);
}
