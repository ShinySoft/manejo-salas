package com.example.manejosalas.entidad;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sala")
@IdClass(SalaId.class)
public class Sala implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7513910887225154174L;

	@Id
    private int id;

	@Id
    private int edificioId;
    
	@Column
	private String nombre;
    
	@Column
	private String tipo;
    
	@Column
	private int capacidad;
    
	@Column
	private int encargado;
    
	@Column
	private String caracteristicas;
    
	@Column
	private int ocupacion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEdificioId() {
		return edificioId;
	}

	public void setEdificioId(int edificioId) {
		this.edificioId = edificioId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getEncargado() {
		return encargado;
	}

	public void setEncargado(int encargado) {
		this.encargado = encargado;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public int getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(int ocupacion) {
		this.ocupacion = ocupacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacidad;
		result = prime * result + ((caracteristicas == null) ? 0 : caracteristicas.hashCode());
		result = prime * result + edificioId;
		result = prime * result + encargado;
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ocupacion;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		if (capacidad != other.capacidad)
			return false;
		if (caracteristicas == null) {
			if (other.caracteristicas != null)
				return false;
		} else if (!caracteristicas.equals(other.caracteristicas))
			return false;
		if (edificioId != other.edificioId)
			return false;
		if (encargado != other.encargado)
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (ocupacion != other.ocupacion)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
    
    /*public Sala (int ID, int edificioID, String nombre, String tipo, int Capacidad, String encargado, boolean sonido, boolean videoBeam, boolean microfono, 
                List<Caracteristica> caracteristicas, List<Horario> horario){
        this.ID = ID;    
        this.edificioID = edificioID;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.encargado = encargado;
        this.sonido = sonido;
        this.videoBeam = videoBeam;
        this.microfono = microfono;
        this.caracteristicas = caracteristicas;
        this.horario = horario;
    }*/



    
}
