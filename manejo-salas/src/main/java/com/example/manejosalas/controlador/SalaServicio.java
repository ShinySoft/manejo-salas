package com.example.manejosalas.controlador;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Usuario;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

public class SalaServicio {
	
	

	@Autowired
	SalaDAO SalaDAO;
	
	@Autowired
	UsuarioDAO usuarioDAO;	
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public Sala buscarSala (Sala sala) throws Exception {
		Sala salaEncontrada = SalaDAO.findByIdAndEdificioId(sala.getId(), sala.getEdificioId());
		if(salaEncontrada != null) {
			return sala;
		}
		throw new Exception("Sala no existente");
	}
	
	public Iterable<Sala> getSalas() {
		return SalaDAO.findAll();
	}

		public void mapSala(Sala salaAModificar,Sala sala2) {
			salaAModificar.setCapacidad(sala2.getCapacidad());
			salaAModificar.setCaracteristicas(sala2.getCaracteristicas());
			salaAModificar.setEdificioId(sala2.getEdificioId());
			salaAModificar.setEncargado(sala2.getEncargado());			
			salaAModificar.setId(sala2.getId());
			salaAModificar.setNombre(sala2.getNombre());
			salaAModificar.setTipo(sala2.getTipo());
		}	
		
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
		
		
		String message = String.format("%s %s su solicitud para la sala ha sido aprobada por ", user.getNombre(), user.getApellido());
		
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
		Usuario admin = (Usuario) usuarioDAO.findById(solicitud.getSalaID().getEncargado().getId()).get();
		
		
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
	
}
