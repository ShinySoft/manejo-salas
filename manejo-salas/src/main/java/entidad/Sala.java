package entidad;

import java.util.ArrayList;
import java.util.List;

public class Sala{
    private int ID;
    private int edificioID;
    private String nombre;
    private String tipo;
    private int capacidad;
    private String encargado;
    private boolean sonido;
    private boolean videoBeam;
    private boolean microfono;
    private List <Caracteristica> caracteristicas = new ArrayList<Caracteristica>();
    private List <Horario> horario = new ArrayList<Horario>();
    
    public Sala (int ID, int edificioID, String nombre, String tipo, int Capacidad, String encargado, boolean sonido, boolean videoBeam, boolean microfono, 
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
    }

    public int getID(){
        return ID;
    }

    public int getEdificioID(){
        return edificioID;
    }

    public String getNombre(){
        return nombre;
    }

    public String getTipo(){
        return tipo;
    }

    public int getCapacidad(){
        return capacidad;
    }

    public String getEncargado(){
        return encargado;
    }

    public List<Caracteristica> getCaracterisicas(){
        return caracteristicas;
    }

    public List<Horario> getHorario(){
        return horario;
    }

    public void setID(int ID){
        this.ID=ID;
    }

    public void setEdificioID(int edificioID){
        this.edificioID = edificioID;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setCapacidad(int capacidad){
        this.capacidad = capacidad;
    }

    public void setEncargado(String encargado){
        this.encargado = encargado;
    }

    public void setCaracterisca (Caracteristica caracteristica){
        this.caracteristicas.add(caracteristica);
    } 

    public void setCaracteristicas (List<Caracteristica> caracteristicas){
        this.caracteristicas = caracteristicas;
    }

    public void setHorario (Horario horario){
        this.horario.add(horario);
    } 

    public void setNuevoHorario (List<Horario> horario){
        this.horario = horario;
    }

    
}
