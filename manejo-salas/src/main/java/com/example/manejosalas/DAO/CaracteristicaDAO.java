package com.example.manejosalas.DAO;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.FetchProfile.FetchOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.manejosalas.entidad.Caracteristica;

@Repository
public interface CaracteristicaDAO extends JpaRepository<Caracteristica, Integer>{
	
	public List<Caracteristica> findAll();
	
	public Caracteristica findById(int id);
		
}