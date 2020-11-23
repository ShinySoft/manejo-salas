package com.example.manejosalas.entidad;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.sql.Date;
import javax.persistence.ManyToOne;
import java.sql.Time; 

@Entity
@Table(name = "solicitud")
public class Solicitud{
	
	@Transient
	private String hora_inicio_temp;
	@Transient
	private String hora_fin_temp;
	
	@Id
    private int id;
	
	@ManyToOne
	@JoinColumn(name="UsuarioId")
    private Usuario usuarioid;
    
	@JoinColumns({
        @JoinColumn(name = "SalaId", referencedColumnName="Id"),
        @JoinColumn(name = "SalaEdificioId", referencedColumnName="EdificioId")
    })
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
	
	@Column
	private String descripcion;
	
	@Column
	private Time hora_inicio;
	
	@Column
	private Time hora_fin;

    /*public Solicitud(int ID, int usuarioID, int salaID, int salaEdificioID, String fechaSolicitud, String fechaPrestamo, String estado){
        this.ID = ID;
        this.usuarioID = usuarioID; 
        this.salaID = salaID;
        this.salaEdificioID = salaEdificioID;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }*/


	public String getHora_fin_temp() {
		return hora_fin_temp;
	}
	
	public String getHora_inicio_temp() {
		return hora_inicio_temp;
	}
	
	public void setHora_fin_temp(String hora_fin_temp) {
		this.hora_fin_temp = hora_fin_temp;
	}
	
	public void setHora_inicio_temp(String hora_inicio_temp) {
		this.hora_inicio_temp = hora_inicio_temp;
	}
	
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

    public String getDescripcion(){
        return descripcion;
    }
    
    public Time getHora_inicio(){
        return hora_inicio;
    }
    
    public Time getHora_fin(){
        return hora_fin;
    }

    public void setDescripcion (String descripcion){
        this.descripcion = descripcion;
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
    
    public void setHora_inicio (Time hora_inicio){
        this.hora_inicio = hora_inicio;
    }
    
    public void setHora_fin (Time hora_fin){
        this.hora_fin = hora_fin;
    }
}