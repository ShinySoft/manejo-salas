package com.example.manejosalas.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Usuario;
import com.example.manejosalas.DAO.SalaDAO;

@RestController
@RequestMapping("salas")
public class SalaControlador extends SalaServicio {
	@Autowired
	SalaDAO salaDAO;

	@GetMapping("/form/cancel")
	public String cancelEditSala(ModelMap model) {
		return "redirect:/sala/add";
	}		
			
	@PostMapping("/add")
	public void add(Sala sala) {
		salaDAO.save(sala);
	}	
	
	@PostMapping("/update/{id}")
	public void update(Model model, @PathVariable(name = "id") int id) {		
		//salaDAO.save(sala);
	}
	
	@PostMapping("/update")
	public String edit(@ModelAttribute("salaForm")Sala sala, BindingResult result, ModelMap model){
		
		model.addAttribute("salaList", salaDAO.findAll());		
				
		return "";
	}	
		
	@PostMapping("/delete/{id}")
	public void delete(int id) {
		salaDAO.deleteById(id);
	}
	
}