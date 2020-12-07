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
//System.out.println();
//System.out.println(date.toString());
//System.out.println();
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

public int yearDate(Date date) {
Calendar da = Calendar.getInstance();
da.setTime(date);
return da.get(Calendar.YEAR);
}

public Boolean[][] ocupacionSemana(int weekc,int edificio_id,int sala_id) {
Calendar de = Calendar.getInstance();
int week= de.get(Calendar.WEEK_OF_YEAR);
int year= de.get(Calendar.YEAR);
if((week + weekc) > 53) {
year++;
week= week + weekc-53;
}else {
week= week + weekc;
}

Boolean matriz[][] = new Boolean[7][7];
Date dia = primerDiaSemana(week,year);
List<Ocupacion> semana = new ArrayList<Ocupacion>();
for (int i = 0; i < 7; i++) {
System.out.println(dia);
semana.add(ocupacionDia(dia,edificio_id,sala_id));
Ocupacion ocu = ocupacionDia(dia,edificio_id,sala_id);
Calendar da = Calendar.getInstance();
da.setTime(dia);
da.add(Calendar.DATE, 1);
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
dia = Date.valueOf(dateFormat.format(da.getTime()));
matriz[i][0] = ocu.getSiete_nueve();
matriz[i][1] = ocu.getNueve_once();
matriz[i][2] = ocu.getOnce_una();
matriz[i][3] = ocu.getDos_cuatro();
matriz[i][4] = ocu.getCuatro_seis();
matriz[i][5] = ocu.getSeis_ocho();
matriz[i][6] = ocu.getOcho_nueve();
}
return matriz;
}

public Ocupacion ocupacionDia(Date date,int edificio_id,int sala_id) {
Ocupacion ocupacion = new Ocupacion();
int day = diaSemana(date);
List<Ocupacion> lista = null;
switch(day) {
case 1:
lista = ocupacionDAO.findOcupacionDomingo(edificio_id,sala_id);
ocupacion.setDomingo(true);
break;
case 2:
lista = ocupacionDAO.findOcupacionLunes(edificio_id,sala_id);
ocupacion.setLunes(true);
break;
case 3:
lista = ocupacionDAO.findOcupacionMartes(edificio_id,sala_id);
ocupacion.setMartes(true);
break;
case 4:
lista = ocupacionDAO.findOcupacionMiercoles(edificio_id,sala_id);
ocupacion.setMiercoles(true);
break;
case 5:
lista = ocupacionDAO.findOcupacionJueves(edificio_id,sala_id);
ocupacion.setJueves(true);
break;
case 6:
lista = ocupacionDAO.findOcupacionViernes(edificio_id,sala_id);
ocupacion.setViernes(true);
break;
case 7:
lista = ocupacionDAO.findOcupacionSabado(edificio_id,sala_id);
ocupacion.setSabado(true);
break;
}
//List<Ocupacion> lista = ocupacionDAO.findOcupacionDia(edificio_id, sala_id, ocupacion.getDomingo(), ocupacion.getLunes(), ocupacion.getMartes(), ocupacion.getMiercoles(), ocupacion.getJueves(), ocupacion.getViernes(), ocupacion.getSabado());
for (Ocupacion oc : lista) {
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
for (Solicitud oc : listas) {
//System.out.println(oc.getFecha_prestamo());
System.out.println(oc.getID());
@SuppressWarnings("deprecation")
int hora_inicio = oc.getHora_inicio().getHours()+5;
System.out.println(oc.getHora_inicio());
@SuppressWarnings("deprecation")
int hora_fin = oc.getHora_fin().getHours()+5;
System.out.println(oc.getHora_fin());
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
System.out.println("ocupacion domingo");
break;
case 2:
ocupacion.setLunes(true);
System.out.println("ocupacion lunes");
break;
case 3:
ocupacion.setMartes(true);
System.out.println("ocupacion martes");
break;
case 4:
ocupacion.setMiercoles(true);
System.out.println("ocupacion miercoles");
break;
case 5:
ocupacion.setJueves(true);
System.out.println("ocupacion jueves");
break;
case 6:
ocupacion.setViernes(true);
System.out.println("ocupacion viernes");
break;
case 7:
ocupacion.setSabado(true);
System.out.println("ocupacion sabado");
break;
}
@SuppressWarnings("deprecation")
int hora_inicio = hora_inicio_time.getHours();
System.out.println(hora_inicio_time);
@SuppressWarnings("deprecation")
int hora_fin = hora_fin_time.getHours();
System.out.println(hora_fin_time);
if(hora_inicio == 7 && hora_fin == 9){
ocupacion.setSiete_nueve(true);
System.out.println("ocupacion 911");
}
if(hora_inicio == 9 && hora_fin == 11){
ocupacion.setNueve_once(true);
//System.out.println("ocupacion 111");
}
if(hora_inicio == 11 && hora_fin == 13){
ocupacion.setOnce_una(true);
//System.out.println("ocupacion 1113");
}
if(hora_inicio == 14 && hora_fin == 16){
ocupacion.setDos_cuatro(true);
//System.out.println("ocupacion 1416");
}
if(hora_inicio == 16 && hora_fin == 18){
ocupacion.setCuatro_seis(true);
//System.out.println("ocupacion 1618");
}
if(hora_inicio == 18 && hora_fin == 20){
ocupacion.setSeis_ocho(true);
//System.out.println("ocupacion 1820");
}
if(hora_inicio == 20 && hora_fin == 21){
//System.out.println("ocupacion 2021");
ocupacion.setOcho_nueve(true);
}
//System.out.println(ocupacionDAO.findOcupacion(edificio_id, sala_id, ocupacion.getDomingo(), ocupacion.getLunes(), ocupacion.getMartes(), ocupacion.getMiercoles(), ocupacion.getJueves(), ocupacion.getViernes(), ocupacion.getSabado(), ocupacion.getSiete_nueve(), ocupacion.getNueve_once(), ocupacion.getOnce_una(), ocupacion.getDos_cuatro(), ocupacion.getCuatro_seis(), ocupacion.getSeis_ocho(), ocupacion.getOcho_nueve()).toString());
if(ocupacionDAO.findOcupacion(edificio_id, sala_id, ocupacion.getDomingo(), ocupacion.getLunes(), ocupacion.getMartes(), ocupacion.getMiercoles(), ocupacion.getJueves(), ocupacion.getViernes(), ocupacion.getSabado(), ocupacion.getSiete_nueve(), ocupacion.getNueve_once(), ocupacion.getOnce_una(), ocupacion.getDos_cuatro(), ocupacion.getCuatro_seis(), ocupacion.getSeis_ocho(), ocupacion.getOcho_nueve()).isEmpty()) {
System.out.println("sin ocupacion");
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
