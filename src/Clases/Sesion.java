package Clases;

import Enums.EstadoSesion;
import jdk.dynalink.SecureLookupSupplier;

import java.time.LocalDateTime;

public class Sesion {
    private static int autoId = 1;
    private int id;
    private int usuarioid;
    private LocalDateTime inicio;
    private EstadoSesion estadoSesion;

    //Metodo constructor
    public Sesion(int usuarioid) {
        this.id = autoId++;
        this.usuarioid = usuarioid;
        inicio = LocalDateTime.now();
        estadoSesion= EstadoSesion.Activa;
    }

    //Setters y Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(int usuarioid) {
        this.usuarioid = usuarioid;
    }

    public EstadoSesion getEstadoSesion() {
        return estadoSesion;
    }

    public void setEstadoSesion(EstadoSesion estadoSesion) {
        this.estadoSesion = estadoSesion;
    }
}
