package com.example.manejosalas.controlador;

import java.util.ArrayList;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.manejosalas.entidad.Caracteristica;

import com.example.manejosalas.entidad.Ocupacion;
import com.example.manejosalas.entidad.Edificio;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.SalaId;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Usuario;
import com.lowagie.text.DocumentException;

import net.sf.jasperreports.engine.JRException;

import com.example.manejosalas.DAO.CaracteristicaDAO;
import com.example.manejosalas.DAO.EdificioDAO;
import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.DAO.SolicitudDAO;
import com.example.manejosalas.DAO.UsuarioDAO;

@RestController
@RequestMapping("salas")
public class SalaControlador extends SalaServicio {
	
	static Sala salaRegistradaSolicitud;
	static String currentUserMail;
	
	@Autowired
	ReporteServicio reporteServicio;
	
	@Autowired
	SalaDAO salaDAO;

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	SolicitudDAO solicitudDAO;
	
	@Autowired
	CaracteristicaDAO caracteristicaDAO;

	@Autowired
	EdificioDAO edificioDAO;
	
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
			return showSalasSuper(model); //super
		}
	
	}


	@GetMapping("/admin/view")
	public ModelAndView showSalasAdmin(Model model) {
							
		String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Usuario admin = usuarioDAO.findByCorreo(userMail);
		
		List<Solicitud> solicitudes = solicitudDAO.findAllBysalaid_encargado_correo(userMail);
		ArrayList<Solicitud>solicitudesPendientes = new ArrayList<Solicitud>();
		ArrayList<Solicitud> solicitudesAux = new ArrayList<Solicitud>();
		List<Caracteristica> caracteristicas = caracteristicaDAO.findAll();
		for (int i=0;i<solicitudes.size();i++) {
			if(solicitudes.get(i).getEstado().equals("PENDIENTE")) {
				solicitudesPendientes.add(solicitudes.get(i));				
			}
			else{
				solicitudesAux.add(solicitudes.get(i));
			}
		}
		ModelAndView modelAndView = new ModelAndView();
		Iterable<Sala> salas = salaDAO.findAllByencargado(admin);			
		
		model.addAttribute("salaRegistro", new Sala());
		model.addAttribute("caracteristica", new Caracteristica());
		modelAndView.setViewName ( "view" );
		model.addAttribute("salaList", salas);
		model.addAttribute("caracteristicaList", caracteristicas);	
		model.addAttribute("listTab","active");
		model.addAttribute("solicitudList", solicitudesAux);
		model.addAttribute("solicitudesPendientesList", solicitudesPendientes);
		model.addAttribute("correoEncargado", userMail);	
		model.addAttribute("categCaracteristica", CategoriaSetUp.getCategorias());
		
		model.addAttribute("adminLogin", "true");

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
		List<Caracteristica> caracteristicas = caracteristicaDAO.findAll();	
		ModelAndView modelAndView = new ModelAndView();
		Iterable<Sala> salas = salaDAO.findAll();
		model.addAttribute("salaRegistro", new Sala());
		modelAndView.setViewName ( "view" );
		model.addAttribute("salaList", salas);
		model.addAttribute("caracteristicaList", caracteristicas);		
		model.addAttribute("listTab","active");
		model.addAttribute("solicitudList", solicitudesAux);
		model.addAttribute("solicitudesPendientesList", solicitudesPendientes);			
		model.addAttribute("categCaracteristica", CategoriaSetUp.getCategorias());
		
		model.addAttribute("userLogin", "true");

		return modelAndView;
	}
	
	@GetMapping("/super/view")
	public ModelAndView showSalasSuper(Model model){
		String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<Solicitud> solicitudes = solicitudDAO.findAll();
		List<Usuario> Admin = usuarioDAO.findAllByPerfil("A");
		ArrayList<Usuario>solicitudesAdmin = new ArrayList<Usuario>();
		List<Edificio> edificios = edificioDAO.findAll();
		for (int i=0;i<Admin.size();i++) {
			if(!Admin.get(i).getEstado()) {
				solicitudesAdmin.add(Admin.get(i));				
			}	
		}
		List<Caracteristica> caracteristicas = caracteristicaDAO.findAll();		
		
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("caracteristicaList", caracteristicas);
		Iterable<Sala> salas = salaDAO.findAll();
		model.addAttribute("salaRegistro", new Sala());
		modelAndView.setViewName ( "view" );
		model.addAttribute("caracteristica", new Caracteristica());
		model.addAttribute("edificio", new Edificio());
		model.addAttribute("sala", new Sala());
		model.addAttribute("salaList", salas);
		model.addAttribute("listTab","active");
		model.addAttribute("encargadosLista",Admin);
		model.addAttribute("edificiosLista",edificios);
		model.addAttribute("solicitudList", solicitudes);	
		model.addAttribute("solicitudesAdminList2", solicitudesAdmin);
		model.addAttribute("categCaracteristica", CategoriaSetUp.getCategorias());
		
		model.addAttribute("superLogin", "true");

		
		return modelAndView;
	}
	
	@GetMapping("/form/cancel")
	public String cancelEditSala(ModelMap model) {
		return "redirect:/salas/view";
	}		
				
	@PostMapping("/admin/add")
	public ModelAndView add(@ModelAttribute("SalaRegistro")Sala sala, BindingResult result, Model model, boolean superEdit) {		
		salaDAO.save(sala);
		return showSalasRoot(model);
	}	
	
	@GetMapping("/admin/show-more/{id}/{edificioId}")
	public ModelAndView update(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		

		List<Caracteristica> allCaracteristicas = caracteristicaDAO.findAll();
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistrada);		
		model.addAttribute("caracteristicas", salaRegistrada.getCaracteristicas());		
		model.addAttribute("caracteristicasAllButSala", allCaracteristicas);		
		
		model.addAttribute("editMode","true");				
		model.addAttribute("disableCriticFields","true");
		
		modelAndView.setViewName ( "salas/sala-form" );	
		
		return modelAndView;
	}
	
	@GetMapping("/super/show-more/{id}/{edificioId}")
	public ModelAndView updateSuper(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		

		List<Caracteristica> allCaracteristicas = caracteristicaDAO.findAll();
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistrada);		
		model.addAttribute("caracteristicas", salaRegistrada.getCaracteristicas());		
		model.addAttribute("caracteristicasAllButSala", allCaracteristicas);		
		
		model.addAttribute("superMode","true");				
		model.addAttribute("disableCriticFields","false");
		
		modelAndView.setViewName ( "salas/sala-form" );	
		
		return modelAndView;
	}
	
	/**
	 * 
	 * @param model
	 * @param id
	 * @param edificioId
	 * @return
	 */
	@GetMapping("/admin/edit-caracteristicas/{tipo-edit}/{edificioId}/{salaId}/{caracteristicaId}")				
	public ModelAndView updateCaracteristica(Model model, @PathVariable(name = "tipo-edit") int tipo_edit, @PathVariable(name = "edificioId") int edificioId, @PathVariable(name = "salaId") int salaId, @PathVariable(name = "caracteristicaId") int caracteristicaId) {
		
		
		Caracteristica nuevaCaracteristica = caracteristicaDAO.findById(caracteristicaId);			
		
		Sala sala = salaDAO.findByIdAndEdificioId(salaId, edificioId);
		
		List<Caracteristica> caracteristicas = sala.getCaracteristicas();
 		
		
		
		//Edit parameter given in the url lets us know if we will add or delete
		if(tipo_edit == 1) {
			//Checks if the caracteristicas has already been added
			if(!caracteristicas.contains(nuevaCaracteristica)){
				caracteristicas.add(nuevaCaracteristica);
			}			
		}
		else {
			caracteristicas.remove(nuevaCaracteristica);
		}
		
		sala.setCaracteristicas(caracteristicas);
		
		
		
		
		salaDAO.save(sala);
		
		ModelAndView modelAndView = new ModelAndView();
		
		

		List<Caracteristica> allCaracteristicas = caracteristicaDAO.findAll();
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", sala);		
		model.addAttribute("caracteristicas", sala.getCaracteristicas());		
		model.addAttribute("caracteristicasAllButSala", allCaracteristicas);
		
		
		model.addAttribute("editMode","true");				
		model.addAttribute("disableCriticFields","true");
		
		modelAndView.setViewName ( "salas/sala-form" );	
		
		return modelAndView;
		
	}	
	
	@GetMapping("/test-caracs-sala/{id}/{edificioId}")
	public List<Caracteristica> testCaracs(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistrada);		
		model.addAttribute("caracteristicas", salaRegistrada.getCaracteristicas());		
		
		
		
		model.addAttribute("editMode","true");				
		model.addAttribute("disableCriticFields","true");
		
		modelAndView.setViewName ( "salas/sala-form" );	
		
		return salaRegistrada.getCaracteristicas();
	}	
	
	@GetMapping("/super/edit-caracteristicas/{tipo-edit}/{edificioId}/{salaId}/{caracteristicaId}")				
	public ModelAndView superupdateCaracteristica(Model model, @PathVariable(name = "tipo-edit") int tipo_edit, @PathVariable(name = "edificioId") int edificioId, @PathVariable(name = "salaId") int salaId, @PathVariable(name = "caracteristicaId") int caracteristicaId) {
		
		
		Caracteristica nuevaCaracteristica = caracteristicaDAO.findById(caracteristicaId);			
		
		Sala sala = salaDAO.findByIdAndEdificioId(salaId, edificioId);
		
		List<Caracteristica> caracteristicas = sala.getCaracteristicas();
 		
		
		
		//Edit parameter given in the url lets us know if we will add or delete
		if(tipo_edit == 1) {
			//Checks if the caracteristicas has already been added
			if(!caracteristicas.contains(nuevaCaracteristica)){
				caracteristicas.add(nuevaCaracteristica);
			}			
		}
		else {
			caracteristicas.remove(nuevaCaracteristica);
		}
		
		sala.setCaracteristicas(caracteristicas);
		
		
		
		
		salaDAO.save(sala);
		
		ModelAndView modelAndView = new ModelAndView();
		
		

		List<Caracteristica> allCaracteristicas = caracteristicaDAO.findAll();
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", sala);		
		model.addAttribute("caracteristicas", sala.getCaracteristicas());		
		model.addAttribute("caracteristicasAllButSala", allCaracteristicas);
		
		
		model.addAttribute("superMode","true");				
		model.addAttribute("disableCriticFields","false");
		
		modelAndView.setViewName ( "salas/sala-form" );	
		
		return modelAndView;
		
	}	
	
	@GetMapping("/super-test-caracs-sala/{id}/{edificioId}")
	public List<Caracteristica> supertestCaracs(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistrada);		
		model.addAttribute("caracteristicas", salaRegistrada.getCaracteristicas());		
		
		
		
		model.addAttribute("superMode","true");				
		model.addAttribute("disableCriticFields","false");
		
		modelAndView.setViewName ( "salas/sala-form" );	
		
		return salaRegistrada.getCaracteristicas();
	}
	
	@GetMapping("/user/show-more/{id}/{edificioId}")
	public ModelAndView showMoreUser(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {
		
		Sala salaRegistrada = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		
		
		model.addAttribute("salaList", salaDAO.findAll());		
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistrada);
		model.addAttribute("caracteristicas", salaRegistrada.getCaracteristicas());			
		
		model.addAttribute("userLogin", "true");
		model.addAttribute("disableFields","true");
		model.addAttribute("disableCriticFields","true");
		
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
		if(requestUser.getPerfil().equals("A")) {
			nuevaSolicitud.setEstado("APROBADA");
		}
		else {
			nuevaSolicitud.setEstado("PENDIENTE");
		}
		
		if(requestUser.getPerfil().equals("A")) {
			model.addAttribute("adminLogin2","true");
		}
		else {
			model.addAttribute("userLogin2","true");
		}
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("editMode","true");
		model.addAttribute("solicitud", nuevaSolicitud);
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));
		model.addAttribute("salaRegistro", salaRegistradaSolicitud);
		model.addAttribute("caracteristicas", salaRegistradaSolicitud.getCaracteristicas());		
		
		
		model.addAttribute("disableFields","true");
		
		modelAndView.setViewName ( "solicitud/solicitud-form" );	
		
		return modelAndView;
	}
		
	@GetMapping("admin/ocupacion/{id}/{edificioId}")
	public ModelAndView ocupacionSala(Model model, @PathVariable(name = "id") int id,  @PathVariable(name = "edificioId") int edificioId) {					
		
		SalaControlador.salaRegistradaSolicitud = salaDAO.findByIdAndEdificioId(id, edificioId);
		
		ModelAndView modelAndView = new ModelAndView();
		
		Ocupacion nuevaOcupacion = new Ocupacion();
						
		nuevaOcupacion.setSalaEdificioId(edificioId);
		nuevaOcupacion.setSalaId(id);
		
		model.addAttribute("salaList", salaDAO.findAll());
		model.addAttribute("editMode","true");
		model.addAttribute("ocupacion", nuevaOcupacion);
		model.addAttribute("encargadoEdit", usuarioDAO.findAllByPerfil("A"));		
		model.addAttribute("caracteristicas", salaRegistradaSolicitud.getCaracteristicas());		
		
		
		model.addAttribute("disableFields","true");
		
		modelAndView.setViewName ( "solicitud/ocupacion-form" );	
		
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
		  sqlTime1.setHours(sqlTime1.getHours());
		  Time sqlTime2 = Time.valueOf(solicitud.getHora_fin_temp()+":00");
		  sqlTime2.setHours(sqlTime2.getHours());
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
	
	@PostMapping("admin/edit")
	public ModelAndView edit(@ModelAttribute("salaRegistro")Sala sala, BindingResult result, ModelMap model){
	
		
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


	@GetMapping("solicitud/accept/{id}")
	public ModelAndView solicitudAccept(Model model,@PathVariable(name = "id") int id) {
		Usuario solicitante = usuarioDAO.findById(id);
		solicitante.setEstado(true);
		sendApprovedAutorization(solicitante.getCorreo());
		usuarioDAO.save(solicitante);
		return showSalasSuper(model);
	}
	
	@GetMapping("solicitud/rehuse/{id}")
	public ModelAndView solicitudRehuse(Model model,@PathVariable(name = "id") int id) {
		Usuario solicitante = usuarioDAO.findById(id);
		sendRejectAutorization(solicitante.getCorreo());
		usuarioDAO.delete(solicitante);
		return showSalasSuper(model);
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
	
	@PostMapping("/super/edificio-register")
	public ModelAndView addEdificio(Model model1, @ModelAttribute("edificio")Edificio edificio, BindingResult result, ModelMap model){
		edificioDAO.save(edificio);
		return showSalasSuper(model1);
	} 
	

	@PostMapping("/super/sala-register")
	public ModelAndView addSala(Model model1, @ModelAttribute("sala")Sala sala, BindingResult result, ModelMap model){
		salaDAO.save(sala);
		return showSalasSuper(model1);
	} 
	
	@GetMapping("/super/generar-reporte/{format}")
	@ResponseBody
	public void generateReport(@PathVariable String format, HttpServletResponse response) throws JRException, SQLException, IOException, DocumentException{
			
		Usuario usuario = usuarioDAO.findByCorreo(currentUserMail);
		
		String ubicacionReporte = reporteServicio.generateReport(usuario.getId(), TipoReporte.SUPER_SALAS);
		
	    String[] pathBroken = ubicacionReporte.split("/");
	    
	    String pathFolder = "";
	    
	    for(int i = 0; i < pathBroken.length - 1; i++){
	    	pathFolder += pathBroken[i] + "/";
	    }
	   	    
				
		String nombreDocumento = reporteServicio.getPagePdf(ubicacionReporte, 3);
		
	      if (nombreDocumento.indexOf(".pdf")>-1) response.setContentType("application/pdf");
	      if (nombreDocumento.indexOf(".html")>-1) response.setContentType("application/html");
	      
	      response.setHeader("Content-Disposition", "attachment; filename=" +nombreDocumento);
	      response.setHeader("Content-Transfer-Encoding", "binary");
	      try {
	    	  BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	    	  FileInputStream fis = new FileInputStream(ubicacionReporte);
	    	  int len;
	    	  byte[] buf = new byte[1024];
	    	  while((len = fis.read(buf)) > 0) {
	    		  bos.write(buf,0,len);
	    	  }
	    	  bos.close();
	    	  response.flushBuffer();
	      }
	      catch(IOException e) {
	    	  e.printStackTrace();
	    	  
	      }		
		
		reporteServicio.deleteDocument(ubicacionReporte);
		

			    
						
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

	





