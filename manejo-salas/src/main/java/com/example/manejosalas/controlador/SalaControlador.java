package com.example.manejosalas.controlador;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.manejosalas.entidad.SalaId;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Usuario;
import com.example.manejosalas.DAO.CaracteristicaDAO;
import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.DAO.SolicitudDAO;
import com.example.manejosalas.DAO.UsuarioDAO;

@RestController
@RequestMapping("salas")
public class SalaControlador extends SalaServicio {
	
	static Sala salaRegistradaSolicitud;
	static String currentUserMail;
	
	@Autowired
	SalaDAO salaDAO;

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	SolicitudDAO solicitudDAO;
	
	@Autowired
	CaracteristicaDAO caracteristicaDAO;
	
	@GetMapping("/")
	public ModelAndView showSalasRoot(Model model) {
	String rol = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
	
	String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
	
	
		
	SalaControlador.currentUserMail = userMail;		
	
		if(rol.equals("[ROLE_ADMIN]")) {
			return showSalasAdmin(model);
		}
		else if (rol.equals("[ROLE_USER]")) {
			return showSalasUser(model);
		}
		else {
			return showSalasUser(model); //super
		}
	
	}


	@GetMapping("/admin/view/")
	public ModelAndView showSalasAdmin(Model model) {
							
		String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Usuario admin = usuarioDAO.findByCorreo(userMail);
		
		List<Solicitud> solicitudes = solicitudDAO.findAllBysalaid_encargado_correo(userMail);
		ArrayList<Solicitud>solicitudesPendientes = new ArrayList<Solicitud>();
		for (int i=0;i<solicitudes.size();i++) {
			if(solicitudes.get(i).getEstado().equals("PENDIENTE")) {
				solicitudesPendientes.add(solicitudes.get(i));
				System.out.println(solicitudesPendientes.size());
			}
		}
		ModelAndView modelAndView = new ModelAndView();
		Iterable<Sala> salas = salaDAO.findAllByencargado(admin);
		List<Caracteristica> caracteristicas = caracteristicaDAO.findAll();		
		
		
		
		
		
		model.addAttribute("salaRegistro", new Sala());
		model.addAttribute("caracteristica", new Caracteristica());
		modelAndView.setViewName ( "view" );
		model.addAttribute("salaList", salas);
		model.addAttribute("listTab","active");
		model.addAttribute("solicitudList", solicitudes);
		model.addAttribute("solicitudesPendientesList", solicitudesPendientes);
		model.addAttribute("correoEncargado", userMail);	
		model.addAttribute("categCaracteristica", CategoriaSetUp.getCategorias());
		
		model.addAttribute("adminLogin", true);
		
		return modelAndView;
	}	
	
	
	@GetMapping("/user/view")
	public ModelAndView showSalasUser(Model model){
		String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
				
		
		ArrayList<Solicitud> solicitudesAux = new ArrayList<Solicitud>();
		
		List<Solicitud> solicitudes = solicitudDAO.findAllByusuarioid_correo(userMail);
		ArrayList<Solicitud>solicitudesPendientes = new ArrayList<Solicitud>();
		for (int i=0;i<solicitudes.size();i++) {
			if(solicitudes.get(i).getEstado().equals("PENDIENTE")) {
				solicitudesPendientes.add(solicitudes.get(i));				
			}			
			else {
				solicitudesAux.add(solicitudes.get(i));
			}
			
		}
		ModelAndView modelAndView = new ModelAndView();
		Iterable<Sala> salas = salaDAO.findAll();
		model.addAttribute("salaRegistro", new Sala());
		modelAndView.setViewName ( "view" );
		model.addAttribute("salaList", salas);
		model.addAttribute("listTab","active");
		model.addAttribute("solicitudList", solicitudesAux);
		model.addAttribute("solicitudesPendientesList", solicitudesPendientes);			
		
		model.addAttribute("userLogin", true);
		
		return modelAndView;
	}
	
	@GetMapping("/form/cancel")
	public String cancelEditSala(ModelMap model) {
		return "redirect:/salas/view";
	}		
				
	@PostMapping("/admin/add")
	public ModelAndView add(@ModelAttribute("SalaRegistro")Sala sala, BindingResult result, Model model) {		
		salaDAO.save(sala);
		return showSalasAdmin(model);
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
	
	
	@GetMapping("/user/show-more/{id}/{edificioId}")
	public ModelAndView showMoreUser(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
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
	
	@GetMapping("all/solicitar/{id}/{edificioId}")
	public ModelAndView updateSolicitud(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Usuario requestUser = usuarioDAO.findByCorreo(SalaControlador.currentUserMail);
		
		
		SalaControlador.salaRegistradaSolicitud = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		Solicitud nuevaSolicitud = new Solicitud();
		
		nuevaSolicitud.setUsuario(requestUser);
		
		//Autocheck for admin request
		if(requestUser.getPerfil() == "A") {
			nuevaSolicitud.setEstado("APROBADA");
		}
		else {
			nuevaSolicitud.setEstado("PENDIENTE");
		}
		
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("editMode","true");
		model.addAttribute("solicitud", nuevaSolicitud);
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistradaSolicitud);
		model.addAttribute("caracteristicas", salaRegistradaSolicitud.getCaracteristicas());
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
		return showSalasAdmin(model);
	}	
	
	@SuppressWarnings("deprecation")
	@PostMapping("/all/solicitud")
	public ModelAndView solicitar(@ModelAttribute("solicitud")Solicitud solicitud, BindingResult result, ModelMap model){

		Usuario requestUser = usuarioDAO.findByCorreo(SalaControlador.currentUserMail);
		
		//Autocheck for admin request
		if((requestUser.getPerfil().equals("A")) || (requestUser.getPerfil().equals("A"))) {
			solicitud.setEstado("APROBADA");
		}
		else {
			solicitud.setEstado("PENDIENTE");
		}
		
		//Gotta check this, 'cause time is not working well
		  Time sqlTime1 = Time.valueOf(solicitud.getHora_inicio_temp()+":00");
		  sqlTime1.setHours(sqlTime1.getHours()-5);
		  Time sqlTime2 = Time.valueOf(solicitud.getHora_fin_temp()+":00");
		  sqlTime2.setHours(sqlTime2.getHours()-5);
		  solicitud.setHora_inicio(sqlTime1);
		  solicitud.setHora_fin(sqlTime2); 		
				
		solicitud.setUsuario(requestUser);
		
		Sala salaSolicitada = salaDAO.findByIdAndEdificioId(SalaControlador.salaRegistradaSolicitud.getId(), SalaControlador.salaRegistradaSolicitud.getEdificioId());
		
		solicitud.setSalaId(salaSolicitada);
		
		solicitudDAO.save(solicitud);

		//Notificate the user and the admin
		sendSalaRequestMade(solicitud);	//User notification
		sendSalaRequestConfirmation(solicitud); //Admin notification
						
		return showSalasRoot((Model)model);
		
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
						
		return showSalasAdmin((Model)model);
	}	
		
	@PostMapping("/delete/{id}/{edificioId}")
	public void delete(int id) {
		salaDAO.deleteById(id);
	}
	
	//En este método deberiamos tomar un comentario del admin
	@GetMapping("/acept/{id}")
	public ModelAndView acept(Model model,@PathVariable(name = "id") int id) {
		Solicitud solicitud = solicitudDAO.findById(id);
		solicitud.setEstado("APROBADA");		
		solicitudDAO.save(solicitud);
		
		//Notificate the user
		sendApprovalConfirmation(solicitud, "Mensaje del admin");
		
		return showSalasAdmin(model);
	}
	
	@GetMapping("/rehuse/{id}")
	public ModelAndView rehuse(Model model,@PathVariable(name = "id") int id) {
		Solicitud solicitud = solicitudDAO.findById(id);
		solicitud.setEstado("RECHAZADA");
		solicitudDAO.save(solicitud);		
		
		//Notificate the user
		sendRejectConfirmation(solicitud, "Mensaje del admin");
		
		return showSalasAdmin(model);
	}

	@GetMapping("/reverse/{id}")
	public ModelAndView reverse(Model model,@PathVariable(name = "id") int id) {
		Solicitud solicitud = solicitudDAO.findById(id);
		if(!solicitud.getEstado().equals("CANCELADA")) {
			java.sql.Date fecha_prestamo= solicitud.getFecha_prestamo();
			long millis = System.currentTimeMillis();  
			java.sql.Date fecha_actual=new java.sql.Date(millis);
			int status=fecha_prestamo.compareTo(fecha_actual);
			if (status==0) {
				Time hora_prestamo = solicitud.getHora_inicio();
		        Time hora_actual = new Time(millis);
				int status_hora = hora_prestamo.compareTo(hora_actual);
				if(status_hora>0) {
					solicitud.setEstado("PENDIENTE");
					solicitudDAO.save(solicitud);	
				}
			}else {
				if(status>0) {
					solicitud.setEstado("PENDIENTE");
					solicitudDAO.save(solicitud);
				}
			}
		}
		return showSalasAdmin(model);
	}
	
	@GetMapping("/cancel/{id}")
	public ModelAndView cancel(Model model,@PathVariable(name = "id") int id) {
		Solicitud solicitud = solicitudDAO.findById(id);
		java.sql.Date fecha_prestamo= solicitud.getFecha_prestamo();
		long millis = System.currentTimeMillis();  
		java.sql.Date fecha_actual=new java.sql.Date(millis);
		int status=fecha_prestamo.compareTo(fecha_actual);
		if (status==0) {
			Time hora_prestamo = solicitud.getHora_inicio();
	        Time hora_actual = new Time(millis);
			int status_hora = hora_prestamo.compareTo(hora_actual);
			if(status_hora>0) {
				solicitud.setEstado("CANCELADA");
				solicitudDAO.save(solicitud);	
			}
		}else {
			if(status>0) {
				solicitud.setEstado("CANCELADA");
				solicitudDAO.save(solicitud);
			}
		}		
		return showSalasUser(model);
	}
	
	@PostMapping("/admin/add-caracteristica")
	public ModelAndView addCaracteristica(Model model1, @ModelAttribute("caracteristica")Caracteristica caracteristica, BindingResult result, ModelMap model){
		caracteristicaDAO.save(caracteristica);
		return showSalasAdmin(model1);
	} 
	
	
}


class CategoriaCaracteristica{
	public String key;
	public String value;
	
	public CategoriaCaracteristica(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	
}

class CategoriaSetUp{
	
	public static String[] caracteristicas = {"S", "E", "I"};
	
	public static CategoriaCaracteristica[] getCategorias(){

		CategoriaCaracteristica[] crs = new CategoriaCaracteristica[caracteristicas.length];
				
			crs[0] = new CategoriaCaracteristica("E", "Equipo");
			crs[1] = new CategoriaCaracteristica("S", "Software");
			crs[2] = new CategoriaCaracteristica("I", "Instalación");
			
			return crs;
		}
		
	}

	





