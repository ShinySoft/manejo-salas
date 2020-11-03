package com.example.manejosalas.controlador;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String index() {
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
		return validacion.getUser();
		
	}
	
	@PostMapping("/register")
	public void register(@RequestBody Usuario usuario) {		
		usuarioDAO.save(usuario);
	}	
	
	@DeleteMapping
	public void delete(@RequestBody Usuario usuario) {
		Usuario usuarioRegistrado = usuarioDAO.findByCorreo(usuario.getCorreo());		
		usuarioDAO.deleteById(usuarioRegistrado.getId());
	}
	
	@GetMapping("/mostrar-todos")
	public String mostrarTodos(Model model) {
		model.addAttribute("userList", usuarioDAO.findAll());
		return "mostrar-todos";
	}		
	
	
	@GetMapping("/test")
	public String test(Model model){
		String message;
		
		message = "F to pay respect";
		model.addAttribute("name", message);
		
		return "index";
	}
	
	
	
	public class UsuarioValidaciones{
		
		public final Usuario usuarioRegistrado;
		public final Usuario usuarioPrueba;
		public UsuarioValidaciones(Usuario usuario) {
			
			usuarioRegistrado = usuarioDAO.findByCorreo(usuario.getCorreo());
			usuarioPrueba = usuario;
		}
		
		private boolean validarCredenciales() {
			if(usuarioRegistrado.getPassword().equals(usuarioPrueba.getPassword())) {
				return true;
			}
			else {
				return false;
			}			
			
		}
		
		public Usuario getUser() throws Exception {
			
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