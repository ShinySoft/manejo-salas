package com.example.manejosalas.controlador;

import com.example.manejosalas.entidad.Usuario;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Usuario;


public class UsuarioServicio{
	
	@Autowired
	UsuarioDAO usuarioDAO;	
	
	public boolean verifyUsuarioEncontrado(Usuario usuario) throws Exception {
		Usuario usuarioEncontrado = usuarioDAO.findByCorreo(usuario.getCorreo());
		if(usuarioEncontrado == null) {
			return true;
		}
		throw new Exception("El usuario ya existe");
	}
	
	public Usuario getUsuarioRegistrado(Usuario usuario) throws Exception {
		Usuario usuarioEncontrado = usuarioDAO.findByCorreo(usuario.getCorreo());
		if(usuarioEncontrado != null) {
			return usuarioEncontrado;
		}
		throw new Exception("El usuario no existe");
	}
	
	public boolean getAdmin(Usuario usuario) {
		if(usuario.getPerfil().equals("A")) {
			return true;
		}
		return false;
	}	
	
	public boolean validarClave(Usuario usuario) throws Exception {
		Usuario usuarioEncontrado = usuarioDAO.findByCorreo(usuario.getCorreo());
		if(usuario.getPassword().equals(usuarioEncontrado.getPassword())){
			return true;
		}
		throw new Exception("La clave es incorrecta");
	}
	
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapUsuario(Usuario from,Usuario to) {
		to.setNombre(from.getNombre());
		to.setApellido(from.getApellido());
		to.setPerfil(from.getPerfil());
		to.setCorreo(from.getCorreo());
		//PODRIAMOS INCLUIR MÁS CAMPOS A MAPEAR
	}

	
}