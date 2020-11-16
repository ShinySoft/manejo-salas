package com.example.manejosalas.controlador;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Usuario;
import com.example.manejosalas.DAO.SalaDAO;

@RestController
@RequestMapping("salas")
public class SalaControlador extends SalaServicio {
	@Autowired
	SalaDAO salaDAO;

	@GetMapping("/")
	public String showSalasRoot(Model model) {
		model.addAttribute("salaRegistro", new Sala());
		model.addAttribute("salaList", salaDAO.findAll());	
		return "view";
	}
	
	@GetMapping("/view")
	public ModelAndView showSalas(Model model) {
		
		ModelAndView modelAndView = new ModelAndView();
		Iterable<Sala> salas = salaDAO.findAll();
		
		model.addAttribute("salaRegistro", new Sala());
		
		modelAndView.setViewName ( "view" );
		model.addAttribute("salaList", salas);
		model.addAttribute("listTab","active");		
		return modelAndView;
	}	
	
	@GetMapping("/todas")
	public Iterable<Sala> todas(){
		return salaDAO.findAll();
	}
	
	@GetMapping("/form/cancel")
	public String cancelEditSala(ModelMap model) {
		return "redirect:/salas/view";
	}		
				
	@PostMapping("/add")
	public ModelAndView add(@ModelAttribute("SalaRegistro")Sala sala, BindingResult result, Model model) {		
		salaDAO.save(sala);
		return showSalas(model);
	}	
	
	@GetMapping("/edit-sala/{id}/{edificioId}")
	public ModelAndView update(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("editMode","true");
		model.addAttribute("salaRegistro", salaRegistrada);
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		model.addAttribute("disableFields","true");
		
		modelAndView.setViewName ( "view" );	
		
		return modelAndView;
	}
	
	@GetMapping("/delete/{id}/{edificioId}")
	public ModelAndView deleteUser(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		try {
			Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
			
			salaDAO.delete(salaRegistrada);
		} 
		catch (Exception uoin) {
			model.addAttribute("listErrorMessage",uoin.getMessage());
		}
		return showSalas(model);
	}	
	
	@PostMapping("/edit")
	public ModelAndView edit(@ModelAttribute("salaForm")Sala sala, BindingResult result, ModelMap model){
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId((int)sala.getId(), (int)sala.getEdificioId());			
		
		try {
			mapSala(salaRegistrada, sala);
			salaDAO.save(salaRegistrada);
		}
		catch(Exception e) {
			model.addAttribute("salaFormErrorMessage", "Error al actualizar la información");
		}
						
		return showSalas((Model)model);
	}	
		
	@PostMapping("/delete/{id}/{edificioId}")
	public void delete(int id) {
		salaDAO.deleteById(id);
	}
	
}
