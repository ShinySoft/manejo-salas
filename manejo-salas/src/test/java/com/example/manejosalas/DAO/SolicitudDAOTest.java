package com.example.manejosalas.DAO;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.manejosalas.entidad.Solicitud;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SolicitudDAOTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private SolicitudDAO solicitudDAO;	
	
	@Test
	public void findById_thenReturnSolicitud() {
		
		Solicitud solicitud = new Solicitud();
		solicitud.setId(123);
		entityManager.persist(solicitud);
		entityManager.flush();		
				
		Solicitud encontrada = solicitudDAO.findById(solicitud.getID());		
		
		assertThat(encontrada.getID()).isEqualTo(solicitud.getID());
	}
	
	@Test
	public void saveSolicitud_inDataBase() {
		Solicitud solicitud = new Solicitud();
		solicitud.setEstado("A");
		//solicitud.setFecha_prestamo('2020/11/11');
		//solicitud.setFecha_solicitud('2020-11-11 23:59:59');
		solicitud.setId(123);
		solicitud.setSalaEdificioId(453);
		solicitud.setSalaId(205);			
	    
		solicitudDAO.save(solicitud);
	    
		assertThat(solicitud).hasFieldOrPropertyWithValue("estado","A");
		assertThat(solicitud).hasFieldOrPropertyWithValue("id",123);
		assertThat(solicitud).hasFieldOrPropertyWithValue("salaedificioid",453);
		assertThat(solicitud).hasFieldOrPropertyWithValue("salaid",205);			
	}	
	
	  @Test
	  public void findSolicitud_byId() {
		  
		Solicitud solicitud = new Solicitud();
		solicitud.setEstado("A");		
		solicitud.setId(123);
		solicitud.setSalaEdificioId(453);
		solicitud.setSalaId(205);
	    entityManager.persist(solicitud);

	    Solicitud solicitud2 = new Solicitud();
		solicitud2.setEstado("A");		
		solicitud2.setId(124);
		solicitud2.setSalaEdificioId(453);
		solicitud2.setSalaId(205);
	    entityManager.persist(solicitud2);	    

	    Solicitud prueba = solicitudDAO.findById(solicitud2.getID());

	    assertThat(prueba).isEqualTo(solicitud2);
	  }	
	  	  
	  @Test
	  public void deleteSolicitud_byId(){
		  
		  Solicitud solicitud = new Solicitud();
		  solicitud.setId(1);		  
		  entityManager.persist(solicitud);
		  
		  Solicitud solicitud2 = new Solicitud();
		  solicitud2.setId(2);		  
		  entityManager.persist(solicitud2);

		  Solicitud solicitud3 = new Solicitud();
		  solicitud3.setId(3);		  
		  entityManager.persist(solicitud3);

		  solicitudDAO.deleteById(solicitud.getID());
		  
		  Iterable<Solicitud> solicitudes = solicitudDAO.findAll();
		  		  
		  assertThat(solicitudes).hasSize(2).contains(solicitud2, solicitud3);		  
	  }

}
