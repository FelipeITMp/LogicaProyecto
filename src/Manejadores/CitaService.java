package Manejadores;

import Clases.Cita;
import Enums.EstadoCita;
import Memoria.CitaMem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CitaService {
    // Creamos una constante y creamos una instacia CitaMem
    final CitaMem cita = new CitaMem();

    //Llamamos a los metodos de CitaMem
    public List<Cita.AgendaItem> agendaDoctor(int IdDoctor, LocalDate fecha){
        return cita.agendaDoctor(IdDoctor,fecha);
    }

    public int crearCita(int PacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estado, String observacion){
        return cita.crear(PacienteId,DoctorId,fecha,hora,estado,observacion);
    }

    public int crearPorCodigos(int PacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estado, String observacion) {
        return cita.crearPorCodigos(PacienteId, DoctorId, fecha, hora, estado, observacion);
    }

    public void actualizarEstado(int citaId, EstadoCita Nuevoestado){
        cita.actualizarEstado(citaId,Nuevoestado);
    }

    public List<Cita.CitaPacItem> citasPaciente(int pacienteid, LocalDate desde , LocalDate hasta){
        return cita.citasPaciente(pacienteid,desde,hasta);
    }

    public int crearPorPaciente(int pacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, String observacion) {
        return cita.crearPorPaciente(pacienteId, DoctorId, fecha, hora, observacion);
    }

    public void cancelarPorPaciente(int citaId, int pacienteId){
        cita.cancelarPorPaciente(citaId,pacienteId);
    }

}
