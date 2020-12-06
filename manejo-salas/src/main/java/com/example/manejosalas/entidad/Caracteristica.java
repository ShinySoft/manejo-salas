package com.example.manejosalas.entidad;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
	private String categoria;

    /*public Caracteristica (int IDCaracteristica, String nombre, String descripcion){
        this.IDCaracteristica = IDCaracteristica;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }*/
    
    @ManyToMany(mappedBy = "caracteristicas")
	private List<Sala> salas;
    
    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getCategoria(){
        return categoria;
    }
    
    public List<Sala> getSalas(){
        return salas;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
    
    public void setSalas(List<Sala> salas){
        this.salas = salas;
    }
}