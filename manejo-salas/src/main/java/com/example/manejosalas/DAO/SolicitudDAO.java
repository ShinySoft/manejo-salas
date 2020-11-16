package com.example.manejosalas.DAO;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.FetchProfile.FetchOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.manejosalas.entidad.Solicitud;

@Repository
public interface SolicitudDAO extends JpaRepository<Solicitud, Integer>{
	
	public List<Solicitud> findAll();
	
	public Solicitud findById(int id);
		
}
