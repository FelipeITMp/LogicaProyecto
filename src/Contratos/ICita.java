package Contratos;

import Clases.Cita;
import Enums.EstadoCita;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICita {
    List<Cita.AgendaItem> agendaDoctor(int doctorId, LocalDate fecha);
    int crear(int pacienteId, int doctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estado, String observacion);
    int crearPorCodigos(int pacienteId, int doctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estado, String observacion);
    void actualizarEstado(int citaId, EstadoCita nuevoEstado);
    List<Cita.CitaPacItem> citasPaciente(int pacienteId, LocalDate desde , LocalDate hasta);
    int crearPorPaciente(int pacienteId, int doctorId, LocalDate fecha, LocalDateTime hora, String observacion);
    void cancelarPorPaciente(int citaId, int pacienteId);
}

