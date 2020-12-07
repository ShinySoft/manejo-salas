package com.example.manejosalas.DAO;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.FetchProfile.FetchOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.SalaSolicitud;
import com.example.manejosalas.entidad.SalaSolicitudSemana;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Usuario;

@Repository
public interface SolicitudDAO extends JpaRepository<Solicitud, Integer>{
	
	public List<Solicitud> findAll();
	
	public Solicitud findById(int id);
	public List <Solicitud> findAllBysalaid_encargado_correo(String correo);
	public List <Solicitud> findAllByusuarioid_correo(String correo);
	
	@Query(value = "SELECT * FROM solicitud WHERE sala_edificio_id = ?1 && sala_id = ?2 && Estado = 'APROBADA' && Fecha_prestamo = ?3 && NOT ((hora_inicio >= ?4 && hora_inicio >= ?5) || (hora_fin <= ?4 && hora_fin <= ?5))", nativeQuery = true)
	public List <Solicitud> findHourByBetween(int sala_edificio_id, int sala_id,Date fecha_prestamo , Time hora_inicio, Time hora_fin );
	
	@Query(value = "SELECT sala_edificio_id AS edificio, sala_id AS sala, COUNT(*) AS solicitudes FROM solicitud WHERE TIMESTAMPDIFF(MONTH, fecha_prestamo, CURDATE()) < 6 && usuario_id = ?1 GROUP BY sala_edificio_id, sala_id", nativeQuery = true)
	public Collection<SalaSolicitud> findConsultas(int id_usuario);
	
	@Query(value = "SELECT DAYNAME(fecha_prestamo) AS dia, COUNT(*) AS solicitudes FROM solicitud WHERE TIMESTAMPDIFF(MONTH, fecha_prestamo, CURDATE()) < 6 AND usuario_id = ?1 GROUP BY DAYNAME(fecha_prestamo);", nativeQuery = true)
	public Collection<SalaSolicitudSemana> findDiaConsultas(int id_usuario);
	
	
}
