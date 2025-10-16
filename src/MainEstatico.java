import Manejadores.*;
import Memoria.UsuarioMem;
import Enums.Rol;
import Enums.EstadoCita;
import Enums.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainEstatico {
    public static void main(String[] args) {
        UsuarioMem memoriaUsuario = new UsuarioMem();
        AuthService autenticacion = new AuthService();
        CitaService cita = new CitaService();
        HistoriaService historia = new HistoriaService();
        NotaService nota = new NotaService();

        int idPac = memoriaUsuario.crear("juan", "1234", Rol.Paciente,
                "1001", "Juan", "Pérez", "jp@mail.com", "123", Genero.MASCULINO,
                "Calle Falsa 123", LocalDate.of(2000,1,1),
                "", "", "");

        int idDoc = memoriaUsuario.crear("ana", "abcd", Rol.Doctor,
                "2001", "Ana", "Gómez", "ag@mail.com", "456", Genero.FEMENINO,
                "", null, "8-12", "Central", "Cardiología");

        var sesionPac = autenticacion.login("juan", "1234");

        LocalDate fecha = LocalDate.now().plusDays(1);
        LocalTime hora = LocalTime.of(10, 0);
        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

        int citaId = cita.crearPorPaciente(sesionPac.pacienteId(), idDoc, fecha, fechaHora, "Chequeo general");

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        var agenda = cita.agendaDoctor(idDoc, fecha);
        for (var c : agenda) {
            System.out.println("Agenda doctor: cita " + c.id + " hora " + c.hora.toLocalTime().format(formatoHora) + " estado " + c.estado);
        }

        cita.actualizarEstado(citaId, EstadoCita.Confirmada);

        var citasPac = cita.citasPaciente(sesionPac.pacienteId(), fecha, fecha);
        for (var c : citasPac) {
            System.out.println("Citas paciente: " + c.id + " fecha " + c.fecha + " hora " + c.hora.toLocalTime().format(formatoHora) + " estado " + c.estado);
        }

        int historiaId = historia.obtenerOCrearHistoria(sesionPac.pacienteId());
        historia.agregarNota(historiaId, "Ninguna", "Ibuprofeno", "Dolor de cabeza", "Reposo");
        var notasH = historia.listarNotas(historiaId);
        for (var n : notasH) {
            System.out.println("Historia nota " + n.getId() +" Alergias: "+ n.getAlergias() +" Medicamentos: "+n.getMedicamentos()+" Motivo: " + n.getMotivoConsulta()+" Recomendaciones: "+n.getRecomendaciones());
        }

        int notaId = nota.ObtenerOCrearNota(sesionPac.pacienteId());
        nota.agregarNota(notaId, "Nota personal: se siente mejor");
        var notasS = nota.listarNotas(notaId);
        for (var n : notasS) {
            System.out.println("Nota simple " + n.getId() + ": " + n.getTexto());
        }

        cita.cancelarPorPaciente(citaId, sesionPac.pacienteId());
        System.out.println("Cita " + citaId + " cancelada");

        autenticacion.logout(sesionPac.sesionId());
        System.out.println("Logout ok");
    }
}
