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
	    sala.setNombre("Lab Microprocesadores");
	    sala.setTipo("Laboratorio");
	    sala.setCapacidad(30);
	    sala.setEncargado(3);   	    
	    
	    salaDAO.save(sala);
	    
		assertThat(sala).hasFieldOrPropertyWithValue("nombre","Lab Microprocesadores");
		assertThat(sala).hasFieldOrPropertyWithValue("tipo","Laboratorio");
		assertThat(sala).hasFieldOrPropertyWithValue("capacidad",30);
		assertThat(sala).hasFieldOrPropertyWithValue("encargado",3);	
	}	
	
	  @Test
	  public void findSala_byId() {
		  
	    Sala sla1 = new Sala();
	    SalaId slaid = new SalaId();
	    sla1.setEdificioId(453);
	    sla1.setId(1);
	    slaid.setId(sla1.getId());
	    slaid.setEdificioId(sla1.getEdificioId());
	    sla1.setEncargado(2);
	    sla1.setTipo("Lab");
	    sla1.setNombre("Laboratorio1");
	    sla1.setCapacidad(30);
	    entityManager.persist(sla1);

	    Sala sla2 = new Sala();
	    SalaId slaid2 = new SalaId();	    	    
	    slaid2.setId(401);
	    slaid2.setEdificioId(454);
	    sla2.setId(401);
	    sla2.setEdificioId(454);
	    sla2.setEncargado(3);
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
	    sla1.setId(1);
	    sla1.setNombre("Laboratorio1");
	    sla1.setCapacidad(30);
	    entityManager.persist(sla1);

	    Sala sla2 = new Sala();
	    sla2.setId(2);
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
		  sla1.setId(1);
		  sla1.setEdificioId(454);	
		  entityManager.persist(sla1);
		  
		  Sala sla2 = new Sala();
		  sla2.setId(2);
		  sla2.setEdificioId(401);		  
		  entityManager.persist(sla2);

		  Sala sla3 = new Sala();
		  sla3.setId(3);
		  sla3.setEdificioId(453);
		  entityManager.persist(sla3);
		  
		  Sala salaRegistrada = salaDAO.findByIdAndEdificioId(sla1.getId(), sla1.getEdificioId());				  		
		  salaDAO.delete(salaRegistrada);
		  
		  Iterable<Sala> salas = salaDAO.findAll();
		  		  
		  assertThat(salas).hasSize(2).contains(sla2, sla3);	
	  }
}
