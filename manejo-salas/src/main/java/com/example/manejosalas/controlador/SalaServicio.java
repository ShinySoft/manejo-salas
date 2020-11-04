package com.example.manejosalas.controlador;

import com.example.manejosalas.entidad.Sala;

public class SalaServicio {

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
