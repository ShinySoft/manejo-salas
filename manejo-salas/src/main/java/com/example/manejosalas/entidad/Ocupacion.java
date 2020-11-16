package com.example.manejosalas.entidad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

import javax.persistence.Column;

@Entity
@Table(name = "ocupacion")
public class Ocupacion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2831648488868113260L;

	@Id
	private int id;
	
	@Column
	private int salaedificioid;
	
	@Column
	private int salaid;
	
	@Column
    private boolean lunes = false;

	@Column
    private boolean martes = false;

	@Column
    private boolean miercoles = false;

	@Column
    private boolean jueves = false;

	@Column
    private boolean viernes = false;

	@Column
    private boolean sabado = false;

	@Column
    private boolean domingo = false;

	@Column(name="siete_nueve")
    private boolean siete_nueve = false;

	@Column(name="nueve_once")
    private boolean nueve_once = false;

	@Column(name="once_una")
    private boolean once_una = false;

	@Column(name="dos_cuatro")
    private boolean dos_cuatro = false;

	@Column(name="cuatro_seis")
    private boolean cuatro_seis = false;

	@Column(name="seis_ocho")
    private boolean seis_ocho = false;

	@Column(name="seis_nueve")
    private boolean seis_nueve = false;

	@Column
    private String descripcion;

    /*public Horario (boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes, boolean sabado,
                    boolean siete_nueve, boolean nueve_once, boolean once_una, boolean dos_cuatro, boolean cuatro_seis, boolean seis_ocho){
        
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.siete_nueve = siete_nueve;
        this.nueve_once = nueve_once;
        this.once_una = once_una;
        this.dos_cuatro = dos_cuatro;
        this.cuatro_seis = cuatro_seis;
        this.seis_ocho = seis_ocho;
           
    }*/

    public boolean getLunes(){
        return lunes;
    }    
    public boolean getMartes(){
        return martes;
    }
    public boolean getMiercoles(){
        return miercoles;
    }
    public boolean getJueves(){
        return jueves;
    }
    public boolean getViernes(){
        return viernes;
    }
    public boolean getSabado(){
        return sabado;
    }

    public boolean getDomingo(){
        return domingo;
    }
    public boolean getSiete_nueve(){
        return siete_nueve;
    }
    public boolean getNueve_once(){
        return nueve_once;
    }
    public boolean getOnce_una(){
        return once_una;
    }
    public boolean getDos_cuatro(){
        return dos_cuatro;
    }
    public boolean getCuatro_seis(){
        return cuatro_seis;
    }
    public boolean getSeis_ocho(){
        return seis_ocho;
    }
    public boolean getSeis_nueve(){
        return seis_nueve;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setLunes(boolean lunes){
        this.lunes = lunes;
    }
    public void setMartes(boolean martes){
        this.martes = martes;
    }
    public void setMiercoles(boolean miercoles){
        this.miercoles = miercoles;
    }
    public void setJueves(boolean jueves){
        this.jueves = jueves;
    }
    public void setViernes(boolean viernes){
        this.viernes = viernes;
    }
    public void setSabado(boolean sabado){
        this.sabado = sabado;
    }
    public void setDomingo(boolean domingo){
        this.domingo = domingo;
    }
    public void setSiete_nueve(boolean siete_nueve){
        this.siete_nueve = siete_nueve;
    }
    public void setNueve_once(boolean nueve_once){
        this.nueve_once = nueve_once;
    }
    public void setOnce_una(boolean once_una){
        this.once_una = once_una;
    }
    public void setDos_cuatro(boolean dos_cuatro){
        this.dos_cuatro = dos_cuatro;
    }
    public void setCuatro_seis(boolean cuatro_seis){
        this.cuatro_seis = cuatro_seis;
    }
    public void setSeis_ocho(boolean seis_ocho){
        this.seis_ocho = seis_ocho;
    }  
    public void setSeis_nueve(boolean seis_nueve){
        this.seis_nueve = seis_nueve;
    }  

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
}
