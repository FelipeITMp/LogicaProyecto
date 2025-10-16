package Clases;

import Enums.Genero;

//Clase padre llamada persona
public class Persona {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private Genero genero;

    //Metodo constructor persona
    public Persona(String cedula, String nombres, String apellidos, String correo, String telefono, Genero genero) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.genero = genero;
    }


    //Setters y Getters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }


    //toString modificado
    @Override
    public String toString() {
        return "Cedula: " + cedula + "\nNombre: " + nombres + "\nApellidos: " + apellidos + "\nCorreo: " + correo + "\nTelefono: " + telefono + "\nGenero: " + genero;
    }
}
