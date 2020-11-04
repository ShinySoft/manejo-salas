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
public class UsuarioControlador extends UsuarioServicio{

	@Autowired
	UsuarioDAO usuarioDAO;		

	@GetMapping("/")
	public String index(Model model) {				
		model.addAttribute("userLogin", new Usuario());
		model.addAttribute("userRegister", new Usuario());
		model.addAttribute("perfiles",Perfil.getPerfiles());						
		model.addAttribute("loginTab","active");		
		return "index";		
	}	
	
	@GetMapping("/register")
	public String register(Model model){
		model.addAttribute("userRegister", new Usuario());
		model.addAttribute("perfiles",Perfil.getPerfiles());
		model.addAttribute("userLogin", new Usuario());		
		model.addAttribute("registerTab","active");		
		return "index";
	}
	
	@GetMapping("/login")
	public String login(Model model) throws Exception {
		model.addAttribute("userLogin", new Usuario());
		model.addAttribute("userRegister", new Usuario());
		model.addAttribute("perfiles",Perfil.getPerfiles());				
		model.addAttribute("loginTab","active");
		return "index";
	}	
		
	@GetMapping("/register/cancel")
	public String cancelRegisterUsuario(ModelMap model) {
		return "redirect:/usuarios/register";
	}
	
	@PostMapping("/register")
	public String registerAction(@ModelAttribute("userRegister")Usuario usuario, BindingResult result, Model model) {
		model.addAttribute("userRegister", new Usuario());
		model.addAttribute("perfiles",Perfil.getPerfiles());
		model.addAttribute("userLogin", new Usuario());			
		model.addAttribute("loginTab","active");	
		
		try {
			verifyUsuarioEncontrado(usuario);
			usuarioDAO.save(usuario);
		}
		catch(Exception e) {
						
		}
		return "index";
	}
	
	@PostMapping("/login")
	public String loginAction(@ModelAttribute("userLogin")Usuario usuario, BindingResult result, Model model) {
		model.addAttribute("userRegister", new Usuario());
		model.addAttribute("perfiles",Perfil.getPerfiles());
		model.addAttribute("userLogin", new Usuario());			
		model.addAttribute("registerTab","active");		
		model.addAttribute("loginTab","active");
		return "index";
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