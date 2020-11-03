package com.example.manejosalas.entidad;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "Sala")
public class Sala implements Serializable{
	@Id
    private int ID;
	
	@Column
    private int edificioID;
    
	@Column
	private String nombre;
    
	@Column
	private String tipo;
    
	@Column
	private int capacidad;
    
	@Column
	private int encargado;
    
	@Column
	private String caracteristicas;
    
	@OneToMany(cascade = CascadeType.ALL)
	private List <Horario> ocupacion = new ArrayList<Horario>();
    
    /*public Sala (int ID, int edificioID, String nombre, String tipo, int Capacidad, String encargado, boolean sonido, boolean videoBeam, boolean microfono, 
                List<Caracteristica> caracteristicas, List<Horario> horario){
        this.ID = ID;    
        this.edificioID = edificioID;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.encargado = encargado;
        this.sonido = sonido;
        this.videoBeam = videoBeam;
        this.microfono = microfono;
        this.caracteristicas = caracteristicas;
        this.horario = horario;
    }*/

    public int getID(){
        return ID;
    }

    public int getEdificioID(){
        return edificioID;
    }

    public String getNombre(){
        return nombre;
    }

    public String getTipo(){
        return tipo;
    }

    public int getCapacidad(){
        return capacidad;
    }

    public int getEncargado(){
        return encargado;
    }

    public String getCaracterisicas(){
        return caracteristicas;
    }

    public List<Horario> getHorario(){
        return ocupacion;
    }

    public void setID(int ID){
        this.ID=ID;
    }

    public void setEdificioID(int edificioID){
        this.edificioID = edificioID;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setCapacidad(int capacidad){
        this.capacidad = capacidad;
    }

    public void setEncargado(int encargado){
        this.encargado = encargado;
    }

    public void setCaracteristicas (String caracteristica){
        this.caracteristicas = (caracteristica);
    } 

    public void setHorario (Horario horario){
        this.ocupacion.add(horario);
    } 

    public void setNuevoHorario (List<Horario> horario){
        this.ocupacion = horario;
    }

    
}
