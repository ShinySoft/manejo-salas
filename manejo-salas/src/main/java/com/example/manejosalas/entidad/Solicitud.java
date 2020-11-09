package com.example.manejosalas.entidad;
import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "solicitud")
public class Solicitud{
	
	@Id
    private int id;
	
	@Column
    private int usuarioid;
    
	@Column
	private int salaid;
    
	@Column
	private int salaedificioid;
    
	@Column
	private String fecha_solicitud;
    
	@Column
	private String fecha_prestamo;
    
	@Column
	private String estado;

    /*public Solicitud(int ID, int usuarioID, int salaID, int salaEdificioID, String fechaSolicitud, String fechaPrestamo, String estado){
        this.ID = ID;
        this.usuarioID = usuarioID; 
        this.salaID = salaID;
        this.salaEdificioID = salaEdificioID;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }*/


    public int getID(){
        return id;
    }
    public int getUsuarioID(){
        return usuarioid;
    }
    public int getSalaID(){
        return salaid;
    }
    public int getSalaEdificioID(){
        return salaedificioid;
    }
    public String getFecha_solicitud(){
        return fecha_solicitud;
    }
    public String getFecha_prestamo(){
        return fecha_prestamo;
    }
    public String getEstado(){
        return estado;
    }

    public void setEstado (String estado){
        this.estado = estado;
    }

    public void setId (int id){
        this.id = id;
    }

    public void setSalaId (int salaid){
        this.salaid = id;
    }
    
    public void setSalaEdificioId (int salaedificioid){
        this.salaedificioid = salaedificioid;
    }

    public void setFecha_solicitud (String fecha_solicitud){
        this.fecha_solicitud = fecha_solicitud;
    }

    public void setFecha_prestamo (String fecha_prestamo){
        this.fecha_prestamo = fecha_prestamo;
    }
}