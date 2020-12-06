package com.example.manejosalas.controlador;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import com.example.manejosalas.DAO.OcupacionDAO;
import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Ocupacion;
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
	
	@Autowired
	OcupacionDAO ocupacionDAO;
	
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
		
		public int diaSemana(Date date) {
			Calendar da = Calendar.getInstance();
			da.setTime(date);
			return da.get(Calendar.DAY_OF_WEEK);
			}

			public Boolean comprobarOcupacion(Date date,Time hora_inicio_time,Time hora_fin_time,int edificio_id,int sala_id){
			Ocupacion ocupacion = new Ocupacion();
			int day = diaSemana(date);
			switch(day) {
			case 1:
			ocupacion.setDomingo(true);
			break;
			case 2:
			ocupacion.setLunes(true);
			break;
			case 3:
			ocupacion.setMartes(true);
			break;
			case 4:
			ocupacion.setMiercoles(true);
			break;
			case 5:
			ocupacion.setJueves(true);
			break;
			case 6:
			ocupacion.setViernes(true);
			break;
			case 7:
			ocupacion.setSabado(true);
			break;
			}
			@SuppressWarnings("deprecation")
			int hora_inicio = hora_inicio_time.getHours();
			@SuppressWarnings("deprecation")
			int hora_fin = hora_fin_time.getHours();
			if(!((hora_inicio < 7 && hora_fin < 7)||(hora_inicio > 9 && hora_fin > 9))){
			ocupacion.setSiete_nueve(true);
			}
			if(!((hora_inicio < 9 && hora_fin < 9)||(hora_inicio > 11 && hora_fin > 11))){
			ocupacion.setNueve_once(true);
			}
			if(!((hora_inicio < 11 && hora_fin < 11)||(hora_inicio > 13 && hora_fin > 13))){
			ocupacion.setOnce_una(true);
			}
			if(!((hora_inicio < 14 && hora_fin < 14)||(hora_inicio > 16 && hora_fin > 16))){
			ocupacion.setDos_cuatro(true);
			}
			if(!((hora_inicio < 16 && hora_fin < 16)||(hora_inicio > 18 && hora_fin > 18))){
			ocupacion.setCuatro_seis(true);
			}
			if(!((hora_inicio < 18 && hora_fin < 18)||(hora_inicio > 20 && hora_fin > 20))){
			ocupacion.setSeis_ocho(true);
			}
			if(!((hora_inicio < 20 && hora_fin < 20)||(hora_inicio > 21 && hora_fin > 21))){
				ocupacion.setOcho_nueve(true);
				}
				if(ocupacionDAO.findOcupacion(edificio_id, sala_id, ocupacion.getDomingo(), ocupacion.getLunes(), ocupacion.getMartes(), ocupacion.getMiercoles(), ocupacion.getJueves(), ocupacion.getViernes(), ocupacion.getSabado(), ocupacion.getSiete_nueve(), ocupacion.getNueve_once(), ocupacion.getOnce_una(), ocupacion.getDos_cuatro(), ocupacion.getCuatro_seis(), ocupacion.getSeis_ocho(), ocupacion.getOcho_nueve()).isEmpty()) {
				return true;
				}


			return false;
			}
		
	
}
