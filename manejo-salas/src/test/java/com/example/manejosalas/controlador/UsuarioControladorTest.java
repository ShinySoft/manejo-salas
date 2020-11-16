package com.example.manejosalas.controlador;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioControlador.class)
public class UsuarioControladorTest {

	//@Autowired
	//private MockMvc mvc;
	
	@MockBean
	private UsuarioDAO usuarioDAO;
	
	@Before
	public void setUp() {
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setCorreo("usuarioPrueba@unal.edu.co");
		
		Mockito.when(usuarioDAO.findByCorreo(usuario.getCorreo())).thenReturn(usuario);
	}
	
	@Test
	public void givenUsuario_whenGetUsuario_thenStatus200() throws Exception {


	}
	
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
