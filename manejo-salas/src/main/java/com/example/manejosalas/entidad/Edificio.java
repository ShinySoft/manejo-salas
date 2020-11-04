package com.example.manejosalas.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sala")
public class Edificio implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3913449754225950969L;
	
	@Id
	private int ID;
	
	@Column
    private String nombre;

	/*
    public Edificio(int ID, String nombre){
        this.ID = ID;
        this.nombre = nombre;
    }
    */

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