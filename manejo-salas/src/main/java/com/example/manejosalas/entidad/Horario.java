package com.example.manejosalas.entidad;

public class Horario {
    private boolean lunes = false;
    private boolean martes = false;
    private boolean miercoles = false;
    private boolean jueves = false;
    private boolean viernes = false;
    private boolean sabado = false;
    private boolean siete_nueve = false;
    private boolean nueve_once = false;
    private boolean once_una = false;
    private boolean dos_cuatro = false;
    private boolean cuatro_seis = false;
    private boolean seis_ocho = false;

    public Horario (boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes, boolean sabado,
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
           
    }

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

}
