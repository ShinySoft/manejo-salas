package controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import DAO.UsuarioDAO;
import entidad.Usuario;

@RestController
@Controller
public class UsuarioControlador {

	@Autowired
	UsuarioDAO usuarioDAO;
		
	@GetMapping(value = {"/", "/login"})
	public String index() {
		return "index";
	}
	

	@PostMapping("/login")
	public void registrar(@RequestBody Usuario usuario) {
		usuarioDAO.save(usuario);
	}
	
	
	@GetMapping("/register")
	public boolean login() {
		return false;
	}

}
