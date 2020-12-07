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

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && Domingo = ?3 && Lunes = ?4 && Martes = ?5 && Miercoles = ?6 && Jueves = ?7 && Viernes = ?8 && Sabado = ?9 && Siete_nueve = ?10 && Nueve_once = ?11 && Once_una = ?12 && Dos_cuatro = ?13 && Cuatro_seis = ?14 && Seis_ocho = ?15 && Ocho_nueve = ?16 LIMIT 1", nativeQuery = true)
public Ocupacion findOcupacionIgual(int sala_edificio_id, int sala_id,Boolean domingo ,Boolean lunes ,Boolean martes ,Boolean miercoles ,Boolean jueves ,Boolean viernes ,Boolean sabado ,Boolean siete_nueve ,Boolean nueve_once ,Boolean once_una,Boolean dos_cuatro,Boolean cuatro_seis, Boolean seis_ocho, Boolean ocho_nueve);

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && ((Domingo = ?3 && Domingo = 1) || (Lunes = ?4 && lunes = 1) || (Martes = ?5 && martes = 1) || (Miercoles = ?6 && Miercoles = 1) || (Jueves = ?7 && Jueves = 1) || (Viernes = ?8 && Viernes = 1) || (Sabado = ?9 && Sabado = 1)) && ((Siete_nueve = ?10 && Siete_nueve = 1) || (Nueve_once = ?11 && Nueve_once = 1) || (Once_una = ?12 && Once_una = 1) || (Dos_cuatro = ?13 && Dos_cuatro = 1) || (Cuatro_seis = ?14 && Cuatro_seis = 1) || (Seis_ocho = ?15 && Seis_ocho = 1) || (Ocho_nueve = ?16 && Ocho_nueve = 1))", nativeQuery = true)
public List <Ocupacion> findOcupacion(int sala_edificio_id, int sala_id,Boolean domingo ,Boolean lunes ,Boolean martes ,Boolean miercoles ,Boolean jueves ,Boolean viernes ,Boolean sabado ,Boolean siete_nueve ,Boolean nueve_once ,Boolean once_una,Boolean dos_cuatro,Boolean cuatro_seis, Boolean seis_ocho, Boolean ocho_nueve);

/*@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && (Domingo = ?3 || Lunes = ?4 || Martes = ?5 || Miercoles = ?6 || Jueves = ?7 || Viernes = ?8 || Sabado = ?9)", nativeQuery = true)
public List <Ocupacion> findOcupacionDia(int sala_edificio_id, int sala_id,Boolean domingo ,Boolean lunes ,Boolean martes ,Boolean miercoles ,Boolean jueves ,Boolean viernes ,Boolean sabado);*/

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && lunes = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionLunes(int edificio_id, int sala_id);

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && martes = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionMartes(int edificio_id, int sala_id);

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && miercoles = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionMiercoles(int edificio_id, int sala_id);

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && jueves = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionJueves(int edificio_id, int sala_id);

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && viernes = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionViernes(int edificio_id, int sala_id);

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && sabado = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionSabado(int edificio_id, int sala_id);

@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && domingo = 1 ", nativeQuery = true)
public List <Ocupacion> findOcupacionDomingo(int edificio_id, int sala_id);
}

//@Query(value = "SELECT * FROM ocupacion WHERE sala_edificio_id = ?1 && sala_id = ?2 && (Domingo = ?3 || Lunes = ?4 || Martes = ?5 || Miercoles = ?6 || Jueves = ?7 || Viernes = ?8 || Sabado = ?9)", nativeQuery = true)
//public List <Ocupacion> findOcupacionDia(int sala_edificio_id, int sala_id,Boolean domingo ,Boolean lunes ,Boolean martes ,Boolean miercoles ,Boolean jueves ,Boolean viernes ,Boolean sabado);}

