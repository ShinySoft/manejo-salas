package com.example.manejosalas.DAO;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.FetchProfile.FetchOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.manejosalas.entidad.Caracteristica;
import com.example.manejosalas.entidad.Sala;

@Repository
public interface SalaDAO extends JpaRepository<Sala,Integer>{
	
	public List<Sala> findAll();
	
	public Sala findById(int id);
}
