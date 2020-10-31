package com.example.manejosalas.DAO;

import static org.assertj.core.api.Assertions.assertThat;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.manejosalas.entidad.Usuario;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsuarioDAOTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Test
	public void findByCorreo_thenReturnUsuario() {
		
		//Given
		Usuario usuario = new Usuario();
		usuario.setCorreo("prueba_correo@unal.edu.co");
		entityManager.persist(usuario);
		entityManager.flush();
		
		
		//When		
		Usuario encontrado = usuarioDAO.findByCorreo(usuario.getCorreo());		
		
		//then
		assertThat(encontrado.getCorreo()).isEqualTo(usuario.getCorreo());
	}
	
	@Test
	public void saveUsuario_inDataBase() {
	    Usuario usuario = new Usuario();
	    usuario.setApellido("Morales");
	    usuario.setNombre("Sara");
	    usuario.setCorreo("smor@unal.edu.co");
	    usuario.setPassword("smor1234");
	    usuario.setPerfil("A");
	    
	    
	    usuarioDAO.save(usuario);
	    
		assertThat(usuario).hasFieldOrPropertyWithValue("nombre","Sara");
		assertThat(usuario).hasFieldOrPropertyWithValue("apellido","Morales");
		assertThat(usuario).hasFieldOrPropertyWithValue("perfil","A");
		assertThat(usuario).hasFieldOrPropertyWithValue("correo","smor@unal.edu.co");		
		assertThat(usuario).hasFieldOrPropertyWithValue("password","smor1234");
	}	
	
	  @Test
	  public void findUsuario_byID() {
		  
	    Usuario usr1 = new Usuario();
	    usr1.setID(1);
	    usr1.setCorreo("f2@unal.edu.co");
	    usr1.setPassword("kkk");
	    entityManager.persist(usr1);

	    Usuario usr2 = new Usuario();
	    usr2.setID(2);
	    usr2.setCorreo("f@unal.edu.co");
	    usr2.setPassword("kkk");
	    entityManager.persist(usr2);

	    Usuario foundTutorial = usuarioDAO.findById(usr2.getID()).get();

	    assertThat(foundTutorial).isEqualTo(usr2);
	  }
	
	  @Test
	  public void updateUsuario_byID() {
	    Usuario usr1 = new Usuario();
	    usr1.setID(1);
	    usr1.setCorreo("correo1@unal.edu.co");
	    usr1.setPassword("rock1234");
	    entityManager.persist(usr1);

	    Usuario usr2 = new Usuario();
	    usr2.setID(2);
	    usr2.setCorreo("correo2@unal.edu.co");
	    usr2.setPassword("crack1234");
	    entityManager.persist(usr2);

	    Usuario updatedUsr2 = new Usuario();
	    updatedUsr2.setID(2);
	    updatedUsr2.setPassword("macarena");

	    Usuario usr = usuarioDAO.findById(usr2.getID()).get();
	    usr.setPassword(updatedUsr2.getPassword());
	    usuarioDAO.save(usr);

	    Usuario checkTut = usuarioDAO.findById(usr2.getID()).get();
	    
	    assertThat(checkTut.getID()).isEqualTo(usr2.getID());
	    assertThat(checkTut.getPassword()).isEqualTo(updatedUsr2.getPassword());	    
	  }	
	  
	  @Test
	  public void deleteUsuario_byID(){
		  
		  Usuario usr1 = new Usuario();
		  usr1.setID(1);
		  
		  entityManager.persist(usr1);
		  
		  Usuario usr2 = new Usuario();
		  usr2.setID(2);

		  entityManager.persist(usr2);

		  Usuario usr3 = new Usuario();
		  usr3.setID(3);

		  entityManager.persist(usr3);

		  usuarioDAO.deleteById(usr1.getID());
		  
		  Iterable<Usuario> usuarios = usuarioDAO.findAll();
		  		  
		  assertThat(usuarios).hasSize(2).contains(usr2, usr3);		  
	  }
}
