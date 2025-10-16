package Clases;

import Enums.Genero;
import java.time.LocalDate;

public class Paciente extends Persona {
    private static int autoId = 1;
    private int id;
    private String direccion;
    private LocalDate fechaNacimiento;
    private int usuarioId;

    //Clase anidada para mostrar los datos de paciente
    public static final class PacienteItem {
        public final int id;
        public final String cedula;
        public final String nombre;

        //Metodo constructor
        public PacienteItem(int id, String cedula, String nombre) {
            this.id = id;
            this.cedula = cedula;
            this.nombre = nombre;
        }

        @Override
        public String toString() { return nombre + " [" + cedula + "]"; }
    }

    //Metodo constructor clase principal
    public Paciente(String cedula, String nombres, String apellidos, String correo, String telefono, Genero genero,
                    String direccion, LocalDate fechaNacimiento, int usuarioId) {
        super(cedula, nombres, apellidos, correo, telefono, genero);
        this.id = autoId++;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.usuarioId = usuarioId;
    }


    //Setters y Getters
    public int getId() { return id; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    @Override
    public String toString() {
        return super.toString() +
                "\nDireccion: " + direccion +
                "\nFecha nacimiento: " + fechaNacimiento +
                "\nUsuarioId: " + usuarioId;
    }
}
