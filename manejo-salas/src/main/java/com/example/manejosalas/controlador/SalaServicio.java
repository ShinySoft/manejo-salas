package com.example.manejosalas.controlador;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.entidad.Sala;

import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Usuario;

public class SalaServicio {
	
	@Autowired
	SalaDAO SalaDAO;
	
	public Sala buscarSala (Sala sala) throws Exception {
		Sala salaEncontrada = SalaDAO.findById(sala.getID());
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
			salaAModificar.setCaracteristicas(sala2.getCaracterisicas());
			salaAModificar.setEdificioID(sala2.getEdificioID());
			salaAModificar.setEncargado(sala2.getEncargado());
			salaAModificar.setNuevoHorario(sala2.getHorario());
			salaAModificar.setID(sala2.getID());
			salaAModificar.setNombre(sala2.getNombre());
			salaAModificar.setTipo(sala2.getTipo());
		}	
		
		
}