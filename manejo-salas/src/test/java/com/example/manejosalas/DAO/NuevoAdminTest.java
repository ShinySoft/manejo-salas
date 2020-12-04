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

public class NuevoAdminTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Test
	  public void newAdminApproved() {
		
	    Usuario usr1 = new Usuario();
	    usr1.setId(1);
	    usr1.setCorreo("admin@unal.edu.co");
	    usr1.setPassword("manejosalas");
	    usr1.setPerfil("A");
	    usr1.setEstado(false);
	    entityManager.persist(usr1);

	    Usuario usr2 = new Usuario();
	    usr2.setId(2);
	    usr2.setCorreo("admin2@unal.edu.co");
	    usr2.setPassword("UNlugar");
	    usr2.setPerfil("A");
	    usr2.setEstado(false);
	    entityManager.persist(usr2);

	    Usuario newadmin = new Usuario();
	    newadmin.setId(2);
	    newadmin.setEstado(true);

	    Usuario usr = usuarioDAO.findById(usr2.getId());
	    usr.setEstado(newadmin.getEstado());
	    usuarioDAO.save(usr);

	    Usuario prueba = usuarioDAO.findById(usr2.getId());
	    
	    assertThat(prueba.getId()).isEqualTo(usr2.getId());
	    assertThat(prueba.getEstado()).isEqualTo(newadmin.getEstado());	    
	  }	
	
	@Test
	  public void newAdminRefused() {
		
	    Usuario usr1 = new Usuario();
	    usr1.setId(1);
	    usr1.setCorreo("admin@unal.edu.co");
	    usr1.setPassword("manejosalas");
	    usr1.setPerfil("A");
	    usr1.setEstado(false);
	    entityManager.persist(usr1);

	    Usuario usr2 = new Usuario();
	    usr2.setId(2);
	    usr2.setCorreo("admin2@unal.edu.co");
	    usr2.setPassword("UNlugar");
	    usr2.setPerfil("A");
	    usr2.setEstado(false);
	    entityManager.persist(usr2);

	    Usuario newadmin = new Usuario();
	    newadmin.setId(2);
	    newadmin.setPerfil("U");
	    newadmin.setEstado(true);

	    Usuario usr = usuarioDAO.findById(usr2.getId());
	    usr.setPerfil(newadmin.getPerfil());
	    usr.setEstado(true);
	    usuarioDAO.save(usr);

	    Usuario prueba = usuarioDAO.findById(usr2.getId());
	    
	    assertThat(prueba.getId()).isEqualTo(usr2.getId());
	    assertThat(prueba.getEstado()).isEqualTo(newadmin.getEstado());
	    assertThat(prueba.getPerfil()).isEqualTo(newadmin.getPerfil());	    
	  }	
}
