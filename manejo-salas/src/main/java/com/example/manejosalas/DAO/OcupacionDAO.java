package com.example.manejosalas.DAO;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.FetchProfile.FetchOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.manejosalas.entidad.Ocupacion;
import com.example.manejosalas.entidad.Solicitud;

@Repository
public interface OcupacionDAO extends JpaRepository<Ocupacion, Integer>{

public List<Ocupacion> findAll();
public Ocupacion findBySalaEdificioIdAndSalaId(int edificio_id, int sala_id);

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && (Domingo = ?3 || Lunes = ?4 || Martes = ?5 || Miercoles = ?6 || Jueves = ?7 || Viernes = ?8 || Sabado = ?9) && (Siete_nueve = ?10 || Nueve_once = ?11 || Once_una = ?12 || Dos_cuatro = ?13 || Cuatro_seis = ?14 || Seis_ocho = ?15 || Ocho_nueve = ?16)", nativeQuery = true)
public List <Ocupacion> findOcupacion(int sala_edificio_id, int sala_id,Boolean domingo ,Boolean lunes ,Boolean martes ,Boolean miercoles ,Boolean jueves ,Boolean viernes ,Boolean sabado ,Boolean siete_nueve ,Boolean nueve_once ,Boolean once_una,Boolean dos_cuatro,Boolean cuatro_seis, Boolean seis_ocho, Boolean ocho_nueve);

@Query(value = "SELECT * FROM ocupacion WHERE lunes = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionLunes();

@Query(value = "SELECT * FROM ocupacion WHERE martes = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionMartes();

@Query(value = "SELECT * FROM ocupacion WHERE miercoles = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionMiercoles();

@Query(value = "SELECT * FROM ocupacion WHERE jueves = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionJueves();

@Query(value = "SELECT * FROM ocupacion WHERE viernes = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionViernes();

@Query(value = "SELECT * FROM ocupacion WHERE sabado = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionSabado();

@Query(value = "SELECT * FROM ocupacion WHERE domingo = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionDomingo();
}

//@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && (Domingo = ?3 || Lunes = ?4 || Martes = ?5 || Miercoles = ?6 || Jueves = ?7 || Viernes = ?8 || Sabado = ?9)", nativeQuery = true)
//public List <Ocupacion> findOcupacionDia(int sala_edificio_id, int sala_id,Boolean domingo ,Boolean lunes ,Boolean martes ,Boolean miercoles ,Boolean jueves ,Boolean viernes ,Boolean sabado);}

