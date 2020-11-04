package com.example.manejosalas.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Usuario;

@Controller
@RequestMapping("/")
public class RootControlador {
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
}
