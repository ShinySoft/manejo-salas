package com.example.manejosalas.DAO;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.SalaId;
import com.example.manejosalas.entidad.Usuario;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SalaDAOTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private SalaDAO salaDAO;
	
	@Test
	public void saveSala_inDataBase() {
	    Sala sala = new Sala();
	    Usuario usr = new Usuario();
	    usr.setId(1);
	    sala.setNombre("Lab Microprocesadores");
	    sala.setTipo("Laboratorio");
	    sala.setCapacidad(30);
	    sala.setEncargado(usr);   	    
	    
	    salaDAO.save(sala);
	    
		assertThat(sala).hasFieldOrPropertyWithValue("nombre","Lab Microprocesadores");
		assertThat(sala).hasFieldOrPropertyWithValue("tipo","Laboratorio");
		assertThat(sala).hasFieldOrPropertyWithValue("capacidad",30);
		assertThat(sala).hasFieldOrPropertyWithValue("encargado", usr);	
	}	
	
	  @Test
	  public void findSala_byId() {
		  
	    Sala sla1 = new Sala();
	    SalaId slaid = new SalaId();
	    Usuario usr = new Usuario();
	    usr.setId(1);
	    sla1.setEdificioId(453);
	    sla1.setId(1);
	    slaid.setId(sla1.getId());
	    slaid.setEdificioId(sla1.getEdificioId());
	    sla1.setEncargado(usr);
	    sla1.setTipo("Lab");
	    sla1.setNombre("Laboratorio1");
	    sla1.setCapacidad(30);
	    entityManager.persist(sla1);

	    Sala sla2 = new Sala();
	    SalaId slaid2 = new SalaId();	
	    Usuario usr2 = new Usuario();
	    usr.setId(1);
	    slaid2.setId(401);
	    slaid2.setEdificioId(454);
	    sla2.setId(401);
	    sla2.setEdificioId(454);
	    sla2.setEncargado(usr2);
	    sla2.setTipo("Lab");
	    sla2.setNombre("Laboratorio2");
	    sla2.setCapacidad(25);	   
	    entityManager.persist(sla2);

	    Sala prueba = salaDAO.findByIdAndEdificioId(sla2.getId(), sla2.getEdificioId());

	    assertThat(prueba).isEqualTo(sla2);
	  }
	
	  @Test
	  public void updateSala_byId() {
	    Sala sla1 = new Sala();	 
	    Usuario enc = new Usuario();
	    enc.setId(1);
	    sla1.setId(1);
	    sla1.setEncargado(enc);
	    sla1.setNombre("Laboratorio1");
	    sla1.setCapacidad(30);
	    entityManager.persist(sla1);

	    Sala sla2 = new Sala();
	    Usuario enc2 = new Usuario();
	    enc2.setId(2);
	    sla2.setId(2);
	    sla2.setEncargado(enc2);
	    sla2.setNombre("Laboratorio2");
	    sla2.setCapacidad(25);
	    entityManager.persist(sla2);

	    Sala updatedSla2 = new Sala();
	    updatedSla2.setId(2);
	    updatedSla2.setCapacidad(35);
	  
	    Sala sla = salaDAO.findByIdAndEdificioId(sla2.getId(), sla2.getEdificioId());
	    sla.setCapacidad(updatedSla2.getCapacidad());
	    salaDAO.save(sla);

	    Sala prueba =salaDAO.findByIdAndEdificioId(sla2.getId(), sla2.getEdificioId());
	    
	    assertThat(prueba.getId()).isEqualTo(sla2.getId());
	    assertThat(prueba.getCapacidad()).isEqualTo(updatedSla2.getCapacidad());	    
	  }	
	  
	  @Test
	  public void deleteSala_byId(){
		  
		  Sala sla1 = new Sala();
		  Usuario enc = new Usuario();
		  enc.setId(1);
		  sla1.setId(1);
		  sla1.setEncargado(enc);
		  sla1.setEdificioId(454);	
		  entityManager.persist(sla1);
		  
		  Sala sla2 = new Sala();
		  Usuario enc2 = new Usuario();
		  enc2.setId(2);
		  sla2.setId(2);
		  sla2.setEncargado(enc2);
		  sla2.setEdificioId(401);		  
		  entityManager.persist(sla2);

		  Sala sla3 = new Sala();
		  Usuario enc3 = new Usuario();
		  enc3.setId(3);
		  sla3.setId(3);
		  sla3.setEncargado(enc3);
		  sla3.setEdificioId(453);
		  entityManager.persist(sla3);
		  
		  Sala salaRegistrada = salaDAO.findByIdAndEdificioId(sla1.getId(), sla1.getEdificioId());				  		
		  salaDAO.delete(salaRegistrada);
		  
		  Iterable<Sala> salas = salaDAO.findAll();
		  		  
		  assertThat(salas).hasSize(2).contains(sla2, sla3);	
	  }
}
