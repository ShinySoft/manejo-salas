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

public class SalaServicio extends CorreoServicio {
	
	

	@Autowired
	SalaDAO SalaDAO;
	
	@Autowired
	UsuarioDAO usuarioDAO;	
	

	
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
		
	
}
