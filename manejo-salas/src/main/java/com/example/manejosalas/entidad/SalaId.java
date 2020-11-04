package com.example.manejosalas.entidad;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class SalaId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9163256900162719096L;
	
	
    private int id;
	
	
    private int edificioId;


	public SalaId(int id, int edificioId) {
		super();
		this.id = id;
		this.edificioId = edificioId;
	}


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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + edificioId;
		result = prime * result + id;
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
		SalaId other = (SalaId) obj;
		if (edificioId != other.edificioId)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	    
}
