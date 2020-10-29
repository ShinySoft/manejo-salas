package com.example.manejosalas;

public class Usuario{
    private int ID;
    private String perfil;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;

    public Usuario(int ID,String perfil, String nombre, String apellido, String correo, String password){
        this.ID = ID;
        this.perfil = perfil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
    }

    public int getID(){
        return ID;
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

    public void setID (int ID){
        this.ID = ID;
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
}