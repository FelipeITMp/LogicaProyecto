package Clases;

import Enums.EstadoCita;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cita {
    //Atributos privados y id automatico
    private static int autoId=1;
    private int id;
    private int pacienteId;
    private int doctorId;
    private LocalDate fecha;
    private LocalDateTime hora;
    private EstadoCita estadoCita;
    private String observacion;

    //Clase anidada para mostrar los datos de agenda
    public static final class AgendaItem {
        public final int id;
        public final LocalDateTime hora;
        public final String paciente;
        public final EstadoCita estado;
        public final String observacion;

        //Metodo Constructor
        public AgendaItem(int id, LocalDateTime hora, int paciente, EstadoCita estado, String observacion) {
            this.id = id;
            this.hora = hora;
            this.paciente = String.valueOf(paciente); //Usamos paciente como String para mostrar en pantalla
            this.estado = estado;
            this.observacion = observacion;
        }
    }

    //Clase anidada para mostrar datos de cita
    public static final class CitaPacItem {
        public final int id;
        public final LocalDate fecha;
        public final LocalDateTime hora;
        public final int doctor;
        public final EstadoCita estado;
        public final String observacion;

        //Metodo Constructor
        public CitaPacItem(int id, LocalDate fecha, LocalDateTime hora, int doctor, EstadoCita estado, String obs) {
            this.id = id;
            this.fecha = fecha;
            this.hora = hora;
            this.doctor = doctor;
            this.estado = estado;
            this.observacion = obs;
        }
    }

    //Metodo constructor de la clase principal
    public Cita(int pacienteId, int doctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estadoCita, String observacion) {
        this.id = autoId++;
        this.pacienteId = pacienteId;
        this.doctorId = doctorId;
        this.fecha = fecha;
        this.hora = hora;
        this.estadoCita = estadoCita;
        this.observacion = observacion;
    }


//Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
