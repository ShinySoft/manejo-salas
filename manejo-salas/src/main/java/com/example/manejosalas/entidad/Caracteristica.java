package com.example.manejosalas.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "caracteristica")
public class Caracteristica implements Serializable{
	@Id
    private int id;
	
	@Column
    private String nombre;
    
	@Column
    private String descripcion;
    
    @Column
	private boolean categoria;

    /*public Caracteristica (int IDCaracteristica, String nombre, String descripcion){
        this.IDCaracteristica = IDCaracteristica;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }*/
	

    public int getID(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public boolean getCategoria(){
        return categoria;
    }

    public void setID(int id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setCategoria(boolean categoria){
        this.categoria = categoria;
    }
}