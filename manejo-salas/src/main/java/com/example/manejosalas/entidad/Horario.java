package com.example.manejosalas.entidad;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "Ocupacion")

public class Horario {
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

	@Column(name="7-9")
    private boolean siete_nueve = false;

	@Column(name="9-11")
    private boolean nueve_once = false;

	@Column(name="11-1")
    private boolean once_una = false;

	@Column(name="2-4")
    private boolean dos_cuatro = false;

	@Column(name="4-6")
    private boolean cuatro_seis = false;

	@Column(name="6-8")
    private boolean seis_ocho = false;

	@Column(name="8-10")
    private boolean ocho_diez = false;

	@Column
    private String razon;

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
    public boolean getOcho_diez(){
        return ocho_diez;
    }
    public String getRazon(){
        return razon;
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
    public void setOcho_diez(boolean ocho_diez){
        this.ocho_diez = ocho_diez;
    }  
    public void setRazon(String razon){
        this.razon = razon;
    } 
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
}
