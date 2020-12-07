package com.example.manejosalas.controlador;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import com.example.manejosalas.DAO.OcupacionDAO;
import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Ocupacion;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Solicitud;
import com.example.manejosalas.entidad.Sala;
import com.example.manejosalas.entidad.Usuario;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

public class SalaServicio extends CorreoServicio {



@Autowired
SalaDAO SalaDAO;

@Autowired
UsuarioDAO usuarioDAO;

@Autowired
OcupacionDAO ocupacionDAO;



public Sala buscarSala (Sala sala) throws Exception {
Sala salaEncontrada = SalaDAO.findByIdAndEdificioId(sala.getId(), sala.getEdificioId());
if(salaEncontrada != null) {
return sala;
}
throw new Exception("Sala no existente");
}

public int diaSemana(Date date) {
Calendar da = Calendar.getInstance();
da.setTime(date);
return da.get(Calendar.DAY_OF_WEEK);
}

public int semanaYear(Date date) {
Calendar da = Calendar.getInstance();
da.setTime(date);
System.out.println();
System.out.println(date.toString());
System.out.println();
return da.get(Calendar.WEEK_OF_YEAR);

}

public Date primerDiaSemana(int week, int year) {
Calendar da = Calendar.getInstance();
da.clear();
da.set(Calendar.WEEK_OF_YEAR, week);
da.set(Calendar.YEAR, year);
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
return Date.valueOf(dateFormat.format(da.getTime()));
}

public List<Ocupacion> ocupacionSemana(int week,int year,int edificio_id,int sala_id) {
Date dia = primerDiaSemana(week,year);
List<Ocupacion> semana = new ArrayList<Ocupacion>();
for (int i = 0; i < 7; i++) {
semana.add(ocupacionDia(dia,edificio_id,sala_id));
Calendar da = Calendar.getInstance();
da.setTime(dia);
da.add(Calendar.DATE, 1);
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
dia = Date.valueOf(dateFormat.format(da.getTime()));
}
return semana;
}

public Ocupacion ocupacionDia(Date date,int edificio_id,int sala_id) {
Ocupacion ocupacion = new Ocupacion();
int day = diaSemana(date);
List<Ocupacion> lista = null;
switch(day) {
case 1:
lista = ocupacionDAO.findOcupacionDomingo();
ocupacion.setDomingo(true);
break;
case 2:
lista = ocupacionDAO.findOcupacionLunes();
ocupacion.setLunes(true);
break;
case 3:
lista = ocupacionDAO.findOcupacionMartes();
ocupacion.setMartes(true);
break;
case 4:
lista = ocupacionDAO.findOcupacionMiercoles();
ocupacion.setMiercoles(true);
break;
case 5:
lista = ocupacionDAO.findOcupacionJueves();
ocupacion.setJueves(true);
break;
case 6:
lista = ocupacionDAO.findOcupacionViernes();
ocupacion.setViernes(true);
break;
case 7:
lista = ocupacionDAO.findOcupacionSabado();
ocupacion.setSabado(true);
break;
}
//List<Ocupacion> lista = ocupacionDAO.findOcupacionDia(edificio_id, sala_id, ocupacion.getDomingo(), ocupacion.getLunes(), ocupacion.getMartes(), ocupacion.getMiercoles(), ocupacion.getJueves(), ocupacion.getViernes(), ocupacion.getSabado());
for (Iterator<Ocupacion> iterator = lista.iterator(); iterator.hasNext();) {
Ocupacion oc = iterator.next();
if(oc.getSiete_nueve()) {
ocupacion.setSiete_nueve(true);
}
if(oc.getNueve_once()) {
ocupacion.setNueve_once(true);
}
if(oc.getOnce_una()) {
ocupacion.setOnce_una(true);
}
if(oc.getDos_cuatro()) {
ocupacion.setDos_cuatro(true);
}
if(oc.getCuatro_seis()) {
ocupacion.setCuatro_seis(true);
}
if(oc.getSeis_ocho()) {
ocupacion.setSeis_ocho(true);
}
if(oc.getOcho_nueve()) {
ocupacion.setOcho_nueve(true);
}
}
List<Solicitud> listas = solicitudDAO.findBydia(edificio_id, sala_id, date);
for (Iterator<Solicitud> iterator = listas.iterator(); iterator.hasNext();) {
Solicitud oc = iterator.next();
@SuppressWarnings("deprecation")
int hora_inicio = oc.getHora_inicio().getHours();
@SuppressWarnings("deprecation")
int hora_fin = oc.getHora_fin().getHours();
if(hora_inicio >= 7 && hora_fin <= 9) {
ocupacion.setSiete_nueve(true);
}
if(hora_inicio >= 9 && hora_fin <= 11) {
ocupacion.setNueve_once(true);
}
if(hora_inicio >= 11 && hora_fin <= 13) {
ocupacion.setOnce_una(true);
}
if(hora_inicio >= 14 && hora_fin <= 16) {
ocupacion.setDos_cuatro(true);
}
if(hora_inicio >= 16 && hora_fin <= 18) {
ocupacion.setCuatro_seis(true);
}
if(hora_inicio >= 18 && hora_fin <= 20) {
ocupacion.setSeis_ocho(true);
}
if(hora_inicio >= 20 && hora_fin <= 21) {
ocupacion.setOcho_nueve(true);
}
}

return ocupacion;

}

public Boolean comprobarOcupacion(Date date,Time hora_inicio_time,Time hora_fin_time,int edificio_id,int sala_id){
Ocupacion ocupacion = new Ocupacion();
int day = diaSemana(date);
switch(day) {
case 1:
ocupacion.setDomingo(true);
break;
case 2:
ocupacion.setLunes(true);
break;
case 3:
ocupacion.setMartes(true);
break;
case 4:
ocupacion.setMiercoles(true);
break;
case 5:
ocupacion.setJueves(true);
break;
case 6:
ocupacion.setViernes(true);
break;
case 7:
ocupacion.setSabado(true);
break;
}
@SuppressWarnings("deprecation")
int hora_inicio = hora_inicio_time.getHours();
@SuppressWarnings("deprecation")
int hora_fin = hora_fin_time.getHours();
if(!((hora_inicio < 7 && hora_fin < 7)||(hora_inicio > 9 && hora_fin > 9))){
ocupacion.setSiete_nueve(true);
}
if(!((hora_inicio < 9 && hora_fin < 9)||(hora_inicio > 11 && hora_fin > 11))){
ocupacion.setNueve_once(true);
}
if(!((hora_inicio < 11 && hora_fin < 11)||(hora_inicio > 13 && hora_fin > 13))){
ocupacion.setOnce_una(true);
}
if(!((hora_inicio < 14 && hora_fin < 14)||(hora_inicio > 16 && hora_fin > 16))){
ocupacion.setDos_cuatro(true);
}
if(!((hora_inicio < 16 && hora_fin < 16)||(hora_inicio > 18 && hora_fin > 18))){
ocupacion.setCuatro_seis(true);
}
if(!((hora_inicio < 18 && hora_fin < 18)||(hora_inicio > 20 && hora_fin > 20))){
ocupacion.setSeis_ocho(true);
}
if(!((hora_inicio < 20 && hora_fin < 20)||(hora_inicio > 21 && hora_fin > 21))){
ocupacion.setOcho_nueve(true);
}
if(ocupacionDAO.findOcupacion(edificio_id, sala_id, ocupacion.getDomingo(), ocupacion.getLunes(), ocupacion.getMartes(), ocupacion.getMiercoles(), ocupacion.getJueves(), ocupacion.getViernes(), ocupacion.getSabado(), ocupacion.getSiete_nueve(), ocupacion.getNueve_once(), ocupacion.getOnce_una(), ocupacion.getDos_cuatro(), ocupacion.getCuatro_seis(), ocupacion.getSeis_ocho(), ocupacion.getOcho_nueve()).isEmpty()) {
return true;
}

return false;
}

public Iterable<Sala> getSalas() {
return SalaDAO.findAll();
}

public void mapSala(Sala salaAModificar,Sala sala2) {
salaAModificar.setCapacidad(sala2.getCapacidad());
salaAModificar.setCaracteristicas(sala2.getCaracteristicas());
salaAModificar.setEdificioId(sala2.getEdificioId());
salaAModificar.setEncargado(sala2.getEncargado());
salaAModificar.setId(sala2.getId());
salaAModificar.setNombre(sala2.getNombre());
salaAModificar.setTipo(sala2.getTipo());
}


}
