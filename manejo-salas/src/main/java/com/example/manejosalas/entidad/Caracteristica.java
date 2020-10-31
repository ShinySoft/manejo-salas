package com.example.manejosalas.entidad;

public class Caracteristica{

    private int IDCaracteristica;
    private String nombre;
    private String descripcion;

    public Caracteristica (int IDCaracteristica, String nombre, String descripcion){
        this.IDCaracteristica = IDCaracteristica;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIDCaracteristica(){
        return IDCaracteristica;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setIDCaracteristica(int IDCaracteristica){
        this.IDCaracteristica = IDCaracteristica;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
}