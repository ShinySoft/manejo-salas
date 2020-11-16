package com.example.manejosalas.entidad;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private int id;
	
	@Column
    private String perfil;
	
	@Column
    private String nombre;
	
	@Column
    private String apellido;
	
	@Column
    private String correo;
	
	@Column
    private String password;
	
	@Column
    private boolean estado;
    
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    //private List<Solicitud> solicitudes;

/*
    public Usuario(int ID,String perfil, String nombre, String apellido, String correo, String password){
        this.ID = ID;
        this.perfil = perfil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
    }
*/
	
	public int getId(){
        return id;
    }

    public String getPerfil(){
        return perfil;
    }
    
    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public String getCorreo(){
        return correo;
    }

    public String getPassword(){
        return password;
    }
    
    public boolean getEstado(){
        return estado;
    }

    public void setId (int ID){
        this.id = ID;
    }

    public void setPerfil (String perfil){
        this.perfil = perfil;
    }
    
    public void setNombre (String nombre){
        this.nombre = nombre;
    }

    public void setApellido (String apellido){
        this.apellido = apellido;
    }

    public void setCorreo (String correo){
        this.correo = correo;
    }

    public void setPassword (String password){
        this.password = password;
    }
    
    public void setEstado (boolean estado){
        this.estado = estado;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Usuario other = (Usuario) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
    
    
       
}