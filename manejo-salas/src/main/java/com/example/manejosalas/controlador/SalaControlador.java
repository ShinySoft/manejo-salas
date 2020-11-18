package com.example.manejosalas.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

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

import com.example.manejosalas.entidad.Caracteristica;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Usuario;
import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.DAO.SolicitudDAO;
import com.example.manejosalas.DAO.UsuarioDAO;

@RestController
@RequestMapping("salas")
public class SalaControlador extends SalaServicio {
	@Autowired
	SalaDAO salaDAO;

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	SolicitudDAO solicitudDAO;

	
	@GetMapping("/")
	public String showSalasRoot(Model model) {
		model.addAttribute("salaRegistro", new Sala());
		model.addAttribute("salaList", salaDAO.findAll());
		
		return "view";
	}
	
	@GetMapping("/view/admin/{id}")
	public ModelAndView showSalas(Model model, @PathVariable(name = "id") int id) {
			
		Usuario encargado = usuarioDAO.findById(id).get();
		List<Solicitud> solicitudes = solicitudDAO.findAllBysalaid_encargado_correo(encargado.getCorreo());
		ArrayList<Solicitud>solicitudesPendientes = new ArrayList<Solicitud>();
		for (int i=0;i<solicitudes.size();i++) {
			//System.out.println(solicitudes.get(i).getEstado() + " 1");
			if(solicitudes.get(i).getEstado().equals("PENDIENTE")) {
				solicitudesPendientes.add(solicitudes.get(i));
				System.out.println(solicitudesPendientes.size());
			}
		}
		ModelAndView modelAndView = new ModelAndView();
		Iterable<Sala> salas = salaDAO.findAll();
		model.addAttribute("salaRegistro", new Sala());
		modelAndView.setViewName ( "view" );
		model.addAttribute("salaList", salas);
		model.addAttribute("listTab","active");
		model.addAttribute("solicitudList", solicitudes);
		model.addAttribute("solicitudesPendientesList", solicitudesPendientes);
		model.addAttribute("correoEncargado", encargado.getCorreo());
		
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
		return showSalas(model, 1);
	}	
	
	@GetMapping("/admin/show-more/{id}/{edificioId}")
	public ModelAndView update(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("editMode","true");
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistrada);
		model.addAttribute("caracteristicas", salaRegistrada.getCaracteristicas());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		model.addAttribute("disableFields","true");
		
		modelAndView.setViewName ( "salas/sala-form" );	
		
		return modelAndView;
	}
	
	@GetMapping("/admin/solicitar/{id}/{edificioId}")
	public ModelAndView updateSolicitud(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		Solicitud nuevaSolicitud = new Solicitud();
		nuevaSolicitud.setSalaId(salaRegistrada);
		nuevaSolicitud.setUsuario(usuarioDAO.findById(1).get());
		nuevaSolicitud.setEstado("PENDIENTE");
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("editMode","true");
		model.addAttribute("solicitud", nuevaSolicitud);
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistrada);
		model.addAttribute("caracteristicas", salaRegistrada.getCaracteristicas());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		model.addAttribute("disableFields","true");
		
		modelAndView.setViewName ( "solicitud/solicitud-form" );	
		
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
		return showSalas(model, 1);
	}	
	
	@PostMapping("/admin/solicitar")
	public ModelAndView solicitar(@ModelAttribute("solicitud")Solicitud solicitud, BindingResult result, ModelMap model){
		
		try {
			solicitudDAO.save(solicitud);
		}
		catch(Exception e) {
			model.addAttribute("salaFormErrorMessage", "Error al actualizar la información");
		}
						
		return showSalas((Model)model, 1);
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
						
		return showSalas((Model)model, 1);
	}	
		
	@PostMapping("/delete/{id}/{edificioId}")
	public void delete(int id) {
		salaDAO.deleteById(id);
	}
	
	@GetMapping("/acept/{id}")
	public ModelAndView acept(Model model,@PathVariable(name = "id") int id) {
		Solicitud solicitud = solicitudDAO.findById(id);
		solicitud.setEstado("APROBADA");
		solicitudDAO.save(solicitud);
		return showSalas(model, 1);
	}
	
	@GetMapping("/rehuse/{id}")
	public ModelAndView rehuse(Model model,@PathVariable(name = "id") int id) {
		Solicitud solicitud = solicitudDAO.findById(id);
		solicitud.setEstado("RECHAZADA");
		solicitudDAO.save(solicitud);
		return showSalas(model, 1);
	}
	
	
	
}
