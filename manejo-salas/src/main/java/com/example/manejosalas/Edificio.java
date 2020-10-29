package com.example.manejosalas;

public class Edificio{
    private int ID;
    private String nombre;

    public Edificio(int ID, String nombre){
        this.ID = ID;
        this.nombre = nombre;
    }

    public int getID(){
        return ID;
    }

    public String getNombre(){
        return nombre;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}