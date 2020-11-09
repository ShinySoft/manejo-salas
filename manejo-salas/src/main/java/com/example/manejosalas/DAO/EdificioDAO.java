package com.example.manejosalas.DAO;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.FetchProfile.FetchOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.manejosalas.entidad.Edificio;

@Repository
public interface EdificioDAO extends JpaRepository<Edificio, Integer>{
	
	public List<Edificio> findAll();
	
	public Edificio findById(int id);
		
}
