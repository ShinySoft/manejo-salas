package com.example.manejosalas.controlador;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Usuario;

@Controller
@RequestMapping("usuarios")
public class UsuarioControlador {

	@Autowired
	UsuarioDAO usuarioDAO;		

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("name", "Santiago");
		return "index";		
	}	
	
	@GetMapping("/register")
	public String register(Model model){
		model.addAttribute("userForm", new Usuario());
		model.addAttribute("userList", usuarioDAO.findAll());
		model.addAttribute("perfiles",Perfil.getPerfiles());		
		model.addAttribute("listTab","active");		
		return "mostrar-todos";
	}
	
	@GetMapping("/login")
	public Usuario login(@RequestBody Usuario usuario) throws Exception {
		UsuarioValidaciones validacion = new UsuarioValidaciones(usuario);
		return validacion.loginGetUser();
		
	}
	
	@GetMapping("/edit-usuario/{correo}")
	public String getEditUsuarioForm(Model model, @PathVariable(name = "correo") String correo) throws Exception {
		Usuario usuarioEdit = usuarioDAO.findByCorreo(correo);
		
		model.addAttribute("userForm", usuarioEdit);
		model.addAttribute("userList", usuarioDAO.findAll());
		model.addAttribute("perfiles",Perfil.getPerfiles());		
		model.addAttribute("formTab","active");
		
		model.addAttribute("editMode", "active");
		
		return "mostrar-todos";
				
	}
		
	@GetMapping("/form/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/usuarios/register";
	}	
	
	@GetMapping("/delete/{id}") 
	public String delete(Model model, @PathVariable(name = "id") int id){
		try {
			Optional<Usuario> usuario = usuarioDAO.findById(id);
			usuarioDAO.delete(usuario.get());
		}
		catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		
		return register(model);
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("userForm")Usuario usuario, BindingResult result, ModelMap model) throws Exception {
		model.addAttribute("userList", usuarioDAO.findAll());
		model.addAttribute("perfiles",Perfil.getPerfiles());	
		
		UsuarioValidaciones validacion = new UsuarioValidaciones(usuario);
		
		try{
			validacion.registerGetUser();
			
			usuarioDAO.save(usuario);
			
			model.addAttribute("userForm", new Usuario());
			model.addAttribute("formTab","active");
		}		
		catch(Exception e) {
			model.addAttribute("formErrorMessage", e.getMessage());			
			model.addAttribute("formTab","active");
		}		
		model.addAttribute("userForm", new Usuario());
		model.addAttribute("userList", usuarioDAO.findAll());
		model.addAttribute("perfiles",Perfil.getPerfiles());			
		return "mostrar-todos";
	}	
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute("userForm")Usuario usuario, BindingResult result, ModelMap model){
		
		model.addAttribute("userList", usuarioDAO.findAll());
		model.addAttribute("perfiles",Perfil.getPerfiles());	
		
		UsuarioValidaciones validacion = new UsuarioValidaciones(usuario);
		
		Usuario registrado = validacion.getUsuarioRegistrado();
		try{
			validacion.mapUsuario(usuario, registrado);
			usuarioDAO.save(registrado);			
			model.addAttribute("userForm", new Usuario());
			model.addAttribute("formTab","active");
		}		
		catch(Exception e) {
			model.addAttribute("formErrorMessage", e.getMessage());			
			model.addAttribute("formTab","active");
			model.addAttribute("editMode", "true"); //Para que no cambie la pantalla y 
													//podamos seguir editando
		}	
		
		model.addAttribute("userForm", new Usuario());
		model.addAttribute("userList", usuarioDAO.findAll());
		model.addAttribute("perfiles",Perfil.getPerfiles());			
		return "mostrar-todos";		
	}
	
	

	public class UsuarioValidaciones{
		
		public final Usuario usuarioRegistrado;

		public final Usuario usuarioPrueba;
		public UsuarioValidaciones(Usuario usuario) {
			
			usuarioRegistrado = usuarioDAO.findByCorreo(usuario.getCorreo());
			usuarioPrueba = usuario;
		}
		
		public Usuario getUsuarioRegistrado() {
			return usuarioRegistrado;
		}

		public Usuario getUsuarioPrueba() {
			return usuarioPrueba;
		}
		
		private boolean validarCredenciales() {
			if(usuarioRegistrado.getPassword().equals(usuarioPrueba.getPassword())) {
				return true;
			}
			else {
				return false;
			}			
			
		}
		
		public Usuario loginGetUser() throws Exception {
			
			if(this.usuarioRegistrado == null) {
				throw new Exception("El usuario no existe");
			}
			else if(!this.validarCredenciales()) {
				throw new Exception("Credenciales incorrectas");
			}
			else {
				return this.usuarioRegistrado;
			}
		}
		
		public boolean registerGetUser() throws Exception{
			
			if(this.usuarioRegistrado == null) {
				return true;
			}
			else {
				throw new Exception("El usuario ya existe");
			}
		}
		
		/**
		 * Map everythin but the password.
		 * @param from
		 * @param to
		 */
		protected void mapUsuario(Usuario from,Usuario to) {
			to.setNombre(from.getNombre());
			to.setApellido(from.getApellido());
			to.setPerfil(from.getPerfil());
			to.setCorreo(from.getCorreo());
			//PODRIAMOS INCLUIR MÁS CAMPOS A MAPEAR
		}

		
	}

}



class Perfil{
	
	private String id;
	private String value;
	
	
	public Perfil(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static Iterable<Perfil> getPerfiles(){
		
		ArrayList<Perfil> perfiles = new ArrayList();
				
		perfiles.add(new Perfil("A", "ADMINISTRADOR"));
		perfiles.add(new Perfil("U", "USUARIO"));
		//perfiles.add(new Perfil(3, "SUPER"));
		return perfiles;	
	}
	
}