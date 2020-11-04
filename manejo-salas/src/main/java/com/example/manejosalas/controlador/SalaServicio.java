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
			salaAModificar.setOcupacion(sala2.getOcupacion());
			salaAModificar.setId(sala2.getId());
			salaAModificar.setNombre(sala2.getNombre());
			salaAModificar.setTipo(sala2.getTipo());
		}	
		
		
}