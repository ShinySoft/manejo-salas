package com.example.manejosalas.controlador;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.manejosalas.DAO.SolicitudDAO;
import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Usuario;

public class CorreoServicio {

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	SolicitudDAO solicitudDAO;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	protected void sendEmail(String to, String body, String topic){
		
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		
		simpleMessage.setFrom("snal99525@gmail.com"); //Email that sends the message (ours)
		
		simpleMessage.setTo(to);
		
		simpleMessage.setSubject(topic);
		
		simpleMessage.setText(body);
		
		javaMailSender.send((SimpleMailMessage) simpleMessage);
	}
	

	protected void sendApprovalConfirmation(Solicitud solicitud, String adminMessage){
		
		Usuario user = (Usuario) usuarioDAO.findById(solicitud.getUsuarioID().getId()).get();			
		Usuario admin = (Usuario) usuarioDAO.findById(solicitud.getSalaID().getEncargado().getId()).get();
		
		
		String message = String.format("UNLugar, %s %s su solicitud para la sala ha sido aprobada por ", user.getNombre(), user.getApellido());
		
		message += String.format("%s %s", admin.getNombre(), admin.getApellido());
		LocalDate localDate = LocalDate.now();//For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String formattedString = localDate.format(formatter);		
		message += String.format(", el %s, detalles: \n \n", formattedString);
		message += String.format("Edificio: %d \n Sala: %d \n", solicitud.getSalaID().getEdificioId(), solicitud.getSalaID().getId());
		
		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(solicitud.getFecha_prestamo());
		
		message += String.format("Fecha: %s \n Comentarios: %s", dateString, adminMessage);
		
		
		
		sendEmail(user.getCorreo(), message, "Solicitud de sala aprobada");
		
	}
	
	protected void sendRejectConfirmation(Solicitud solicitud, String adminMessage){
		
		Usuario user = (Usuario) usuarioDAO.findById(solicitud.getUsuarioID().getId()).get();
		Usuario admin = (Usuario) usuarioDAO.findById(solicitud.getSalaID().getEncargado().getId()).get();
		
		String message = String.format("%s %s su solicitud para la sala ha sido rechazada:\n \n", user.getNombre(), user.getApellido());
		message += String.format("Encargado: %s\n", admin.getCorreo());
		message += String.format("Comentarios: %s", adminMessage);
		
		
		sendEmail(user.getCorreo(), message, "Solicitud de sala rechazada");
	}	
	
	/**
	 * This method manage the email that's send to the user
	 * that made the request.
	 * @param solicitud
	 */
	protected void sendSalaRequestMade(Solicitud solicitud){
		
		Usuario user = (Usuario) usuarioDAO.findById(solicitud.getUsuarioID().getId()).get();					
		
		
		String message = String.format("%s %s su solicitud para la sala realizada", user.getNombre(), user.getApellido());
				
		LocalDate localDate = LocalDate.now();//For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String formattedString = localDate.format(formatter);
		message += String.format(" el %s ", formattedString);
		message += "se encuentra pendiente de aprobación, detalles: \n \n";
		message += String.format("Edificio: %d \n Sala: %d \n", solicitud.getSalaID().getEdificioId(), solicitud.getSalaID().getId());
		
		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(solicitud.getFecha_prestamo());
		
		message += String.format("Fecha: %s \n", dateString);
		
		sendEmail(user.getCorreo(), message, "Solicitud de sala realizada");
		
	}	
		
	/**
	 * This method manage the email that's send to the 
	 * admin on charge of the room.
	 * @param solicitud
	 */
	protected void sendSalaRequestConfirmation(Solicitud solicitud){
		
		Usuario admin = (Usuario) usuarioDAO.findById(solicitud.getSalaID().getEncargado().getId()).get();
		
		String message = "Una nueva solicitud realizada";		
		LocalDate localDate = LocalDate.now();//For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String formattedString = localDate.format(formatter);
		message += String.format(" el %s ", formattedString);
		message += "se encuentra pendiente de aprobación, detalles: \n \n";
		message += String.format("Edificio: %d \n Sala: %d \n", solicitud.getSalaID().getEdificioId(), solicitud.getSalaID().getId());
		
		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(solicitud.getFecha_prestamo());
		
		message += String.format("Fecha: %s \n", dateString);
		
		sendEmail(admin.getCorreo(), message, "Nueva solicitud de sala");
	}
	
	

	
	protected void sendVerificationToken(Usuario usuario){
		String validationMessage = "Bienvenido a UNLugar, con el siguiente link podrá activar su cuenta:\n";
		
		usuario = usuarioDAO.findByCorreo(usuario.getCorreo());
		
		//Gotta change for the URL where the server is hosted (in our case the ipv4 of the EC2 instance)
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
