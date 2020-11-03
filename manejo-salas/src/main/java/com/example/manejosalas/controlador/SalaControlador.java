package com.example.manejosalas.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.DAO.SalaDAO;

@RestController
@RequestMapping("salas")
public class SalaControlador {
	@Autowired
	SalaDAO salaDAO;
	
	@GetMapping
	public List <Sala> findAll(){
		List<Sala> salas = salaDAO.findAll();
		return salas;
	}
	
	@GetMapping
	public Sala findById(int id){
		Sala sala = salaDAO.findById(id);
		return sala;
	}
	
	@PostMapping()
	public void insert(Sala sala) {
		salaDAO.save(sala);
	}
	
	@PostMapping()
	public void delete(int id) {
		salaDAO.deleteById(id);
	}
	
	@PostMapping()
	public void update(Sala sala) {
		Sala salaAModificar = findById(sala.getID());
		salaAModificar.setCapacidad(sala.getCapacidad());
		salaAModificar.setCaracteristicas(sala.getCaracterisicas());
		salaAModificar.setEdificioID(sala.getEdificioID());
		salaAModificar.setEncargado(sala.getEncargado());
		salaAModificar.setNuevoHorario(sala.getHorario());
		salaAModificar.setID(sala.getID());
		salaAModificar.setNombre(sala.getNombre());
		salaAModificar.setTipo(sala.getTipo());
		salaDAO.save(sala);
	}
	
}
