package com.example.manejosalas.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Usuario;

@RestController
@RequestMapping("usuarios")
public class UsuarioControlador {

	@Autowired
	UsuarioDAO usuarioDAO;		


	@PostMapping("/register")
	public void register(@RequestBody Usuario usuario) {		
		usuarioDAO.save(usuario);
	}
	
	
	@GetMapping("/login")
	public Usuario login(@RequestBody Usuario usuario) throws Exception {
		UsuarioValidaciones validacion = new UsuarioValidaciones(usuario);
		return validacion.getUser();
		
	}
	
	@DeleteMapping
	public void delete(@RequestBody Usuario usuario) {
		Usuario usuarioRegistrado = usuarioDAO.findByCorreo(usuario.getCorreo());
		usuarioDAO.deleteById(usuarioRegistrado.getID());
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



enum Perfil{
    ADMIN("A"),
    SOLICITANTE("S")
    ;

    private final String text;

    /**
     * @param text
     */
    Perfil(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}