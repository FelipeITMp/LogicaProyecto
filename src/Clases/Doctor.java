package Clases;

import Enums.Genero;

//Heredamos de Persona para acortar los atributos
public class Doctor extends Persona {

    private static int autoid = 1;
    private int id;
    private String especialidad;
    private String sede;
    private String horario;
    private int usuarioId;

    //Clase anidada para mostrar los datos de doctor
    public static final class DoctorItem {
        public final int id;
        public final String nombre;
        public final String especialidad;

        //Constructor
        public DoctorItem(int id, String nombre, String especialidad) {
            this.id = id;
            this.nombre = nombre;
            this.especialidad = especialidad;
        }
    }

    //Metodo constructor principal
    public Doctor(String cedula, String nombres, String apellidos, String correo, String telefono, Genero genero,
                  String horario, String sede, String especialidad, int usuarioId) {
        super(cedula, nombres, apellidos, correo, telefono, genero);
        this.id = autoid++;
        this.horario = horario;
        this.sede = sede;
        this.especialidad = especialidad;
        this.usuarioId = usuarioId;
    }


    //Getters y Setters
    public int getId() { return id; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}
