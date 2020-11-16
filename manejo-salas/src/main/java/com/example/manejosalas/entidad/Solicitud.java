package com.example.manejosalas.entidad;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.sql.Date;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "solicitud")
public class Solicitud{
	
	@Id
    private int id;
	
	@ManyToOne
    private Usuario usuarioid;
    
	@ManyToOne
	private Sala salaid;
    
	//@ManyToOne
	//private Edificio salaedificioid;
    
	@Column
	private Timestamp fecha_solicitud;
    
	@Column
	private Date fecha_prestamo;
    
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
    public Usuario getUsuarioID(){
        return usuarioid;
    }
    public Sala getSalaID(){
        return salaid;
    }
    /*public Edificio getSalaEdificioID(){
        return salaedificioid;
    }*/
    public Timestamp getFecha_solicitud(){
        return fecha_solicitud;
    }
    public Date getFecha_prestamo(){
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


    public void setUsuario(Usuario usuario){
        this.usuarioid = usuario;
    }

    public void setSalaId (Sala salaid){

        this.salaid = salaid;
    }
    
    /*public void setSalaEdificioId (Edificio salaedificioid){
        this.salaedificioid = salaedificioid;
    }*/

    public void setFecha_solicitud (Timestamp fecha_solicitud){
        this.fecha_solicitud = fecha_solicitud;
    }

    public void setFecha_prestamo (Date fecha_prestamo){
        this.fecha_prestamo = fecha_prestamo;
    }
}