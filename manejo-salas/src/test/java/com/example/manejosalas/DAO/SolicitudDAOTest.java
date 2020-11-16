package com.example.manejosalas.DAO;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Usuario;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SolicitudDAOTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private SolicitudDAO solicitudDAO;	
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private SalaDAO salaDAO;
	
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
		Usuario usr = new Usuario();
		Sala sla =new Sala();
		usr = usuarioDAO.findByCorreo("jgarzo@unal.edu.co");
		sla = salaDAO.findByIdAndEdificioId(1, 4);			
		solicitud.setEstado("A");
		//solicitud.setFecha_prestamo('2020/11/11');
		//solicitud.setFecha_solicitud('2020-11-11 23:59:59');
		solicitud.setId(123);			
		solicitud.setSalaId(sla);	
		solicitud.setUsuario(usr);
	    
		solicitudDAO.save(solicitud);
	    
		assertThat(solicitud).hasFieldOrPropertyWithValue("estado","A");
		assertThat(solicitud).hasFieldOrPropertyWithValue("id",123);
		assertThat(solicitud).hasFieldOrPropertyWithValue("salaid", sla);	
		assertThat(solicitud).hasFieldOrPropertyWithValue("usuarioid", usr);
	}	
	
	  @Test
	  public void findSolicitud_byId() {
		  
		Solicitud solicitud = new Solicitud();
		Sala sla =new Sala();
		sla.setId(205);
		sla.setEdificioId(453);
		solicitud.setEstado("A");		
		solicitud.setId(123);
		solicitud.setSalaId(sla);
	    entityManager.persist(solicitud);

	    Solicitud solicitud2 = new Solicitud();
	    Sala sla2 =new Sala();
		sla2.setId(205);
		sla2.setEdificioId(453);
		solicitud2.setSalaId(sla2);
		solicitud2.setEstado("A");		
		solicitud2.setId(124);
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
