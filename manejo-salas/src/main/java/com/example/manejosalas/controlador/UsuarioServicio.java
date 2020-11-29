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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

public class UsuarioServicio{
	
	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public boolean verifyUsuarioEncontrado(Usuario usuario) throws Exception {
		Usuario usuarioEncontrado = usuarioDAO.findByCorreo(usuario.getCorreo());
		if(usuarioEncontrado == null) {
			return false;
		}
		
		return true;
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
	 * Map everything but the password.
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

	protected void sendEmail(String to, String body, String topic){
		
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		
		simpleMessage.setFrom("snal99525@gmail.com");
		
		simpleMessage.setTo(to);
		
		simpleMessage.setSubject(topic);
		
		simpleMessage.setText(body);
		
		javaMailSender.send((SimpleMailMessage) simpleMessage);
	}
	
	protected void sendVerificationToken(Usuario usuario){
		String validationMessage = "Bienvenido a UNLugar, con el siguiente link podrá activar su cuenta:\n";
		
		usuario = usuarioDAO.findByCorreo(usuario.getCorreo());
		
		//Gotta change for the URL where the server is hosted
		validationMessage += String.format("localhost:8080/usuarios/activate-user/%d/%d", usuario.getId(), usuario.hashCode()); 
		
		//Here is where we send the code via email
		sendEmail(usuario.getCorreo(), validationMessage, "Activación cuenta UNLugar");			
	}
	
	protected void sendWaitingAutorization(String correo){
		String message = "Querido usuario de UNLugar, su solicitud de registro está en espera de ser aprobada.";
				
		//Here is where we send the code via email
		sendEmail(correo, message, "Activación pendiente cuenta UNLugar");			
	}	
	
}