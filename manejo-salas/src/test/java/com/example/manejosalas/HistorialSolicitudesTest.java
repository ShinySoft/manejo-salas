package com.example.manejosalas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.DAO.SolicitudDAO;
import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Usuario;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HistorialSolicitudesTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private SolicitudDAO solicitudDAO;	
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private SalaDAO salaDAO;

	@Test
	public void requestRecord() {
		
		Solicitud solicitud1 = new Solicitud();
		Usuario user1 = new Usuario();
		Sala sala1 =new Sala();
		user1 = usuarioDAO.findByCorreo("judgonzalezmu@unal.edu.co");
		sala1 = salaDAO.findByIdAndEdificioId(1, 4);			
		solicitud1.setEstado("A");		
		solicitud1.setId(121);			
		solicitud1.setSalaId(sala1);	
		solicitud1.setUsuario(user1);
		entityManager.persist(solicitud1);
		
		Solicitud solicitud2 = new Solicitud();
		Usuario user2 = new Usuario();
		Sala sala2 =new Sala();
		user2 = usuarioDAO.findByCorreo("judgonzalezmu@unal.edu.co");
		sala2 = salaDAO.findByIdAndEdificioId(102, 4);			
		solicitud2.setEstado("A");		
		solicitud2.setId(122);			
		solicitud2.setSalaId(sala2);	
		solicitud2.setUsuario(user2);
		entityManager.persist(solicitud2);
		
		Solicitud solicitud3 = new Solicitud();
		Usuario user3 = new Usuario();
		Sala sala3 =new Sala();
		user3 = usuarioDAO.findByCorreo("judgonzalezmu@unal.edu.co");
		sala3 = salaDAO.findByIdAndEdificioId(103, 4);			
		solicitud3.setEstado("A");		
		solicitud3.setId(123);			
		solicitud3.setSalaId(sala3);	
		solicitud3.setUsuario(user3);
		entityManager.persist(solicitud3);
		
		Solicitud solicitud4 = new Solicitud();
		Usuario user4 = new Usuario();
		Sala sala4 =new Sala();
		user4 = usuarioDAO.findByCorreo("judgonzalezmu@unal.edu.co");
		sala4 = salaDAO.findByIdAndEdificioId(104, 4);			
		solicitud4.setEstado("A");		
		solicitud4.setId(124);			
		solicitud4.setSalaId(sala4);	
		solicitud4.setUsuario(user4);
		entityManager.persist(solicitud4);
		
		Solicitud solicitud5 = new Solicitud();
		Usuario user5 = new Usuario();
		Sala sala5 =new Sala();
		user5 = usuarioDAO.findByCorreo("judgonzalezmu@unal.edu.co");
		sala5 = salaDAO.findByIdAndEdificioId(1, 5);			
		solicitud5.setEstado("A");		
		solicitud5.setId(125);			
		solicitud5.setSalaId(sala5);	
		solicitud5.setUsuario(user5);
		entityManager.persist(solicitud5);
		
		Iterable<Solicitud> solicitudes = solicitudDAO.findAll();
		
		assertThat(solicitudes).hasSize(5).contains(solicitud1, solicitud2, solicitud3, solicitud4, solicitud5);		
	}

}
