package com.example.manejosalas.entidad;
//INCOMPLETA
public class Solicitud{
    private int ID;
    private int usuarioID;
    private int salaID;
    private int salaEdificioID;
    private String fechaSolicitud;
    private String fechaPrestamo;
    private String estado;

    public Solicitud(int ID, int usuarioID, int salaID, int salaEdificioID, String fechaSolicitud, String fechaPrestamo, String estado){
        this.ID = ID;
        this.usuarioID = usuarioID; 
        this.salaID = salaID;
        this.salaEdificioID = salaEdificioID;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }


    public int getID(){
        return ID;
    }
    public int getUsuarioID(){
        return usuarioID;
    }
    public int getSalaID(){
        return salaID;
    }
    public int getSalaEdificioID(){
        return salaEdificioID;
    }
    public String getFechaSolicitud(){
        return fechaSolicitud;
    }
    public String getFechaPrestamo(){
        return fechaPrestamo;
    }
    public String getEstado(){
        return estado;
    }

    public void setEstado (String estado){
        this.estado = estado;
    }
}