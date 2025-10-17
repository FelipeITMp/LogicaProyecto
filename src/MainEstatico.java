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
        //Creamos todos los objetos de memoria y servicios que se van a usar en el test
        UsuarioMem memoriaUsuario = new UsuarioMem();
        AuthService autenticacion = new AuthService();
        CitaService cita = new CitaService();
        HistoriaService historia = new HistoriaService();
        NotaService nota = new NotaService();

        //Creamos el paciente y guardamos su id en idPac
        int idPac = memoriaUsuario.crear("juan", "1234", Rol.Paciente,
                "1001", "Juan", "Pérez", "jp@mail.com", "123", Genero.MASCULINO,
                "Calle Falsa 123", LocalDate.of(2000,1,1),
                "", "", "");
        //Creamos el doctor y guardamos su id en idDoc
        int idDoc = memoriaUsuario.crear("ana", "abcd", Rol.Doctor,
                "2001", "Ana", "Gómez", "ag@mail.com", "456", Genero.FEMENINO,
                "", null, "8-12", "Central", "Cardiología");

        //Creamos un objeto LoginResult llamado sesionPac
        var sesionPac = autenticacion.login("juan", "1234");

        //Creamos las fechas de una cita para el día siguiente al actual
        LocalDate fecha = LocalDate.now().plusDays(1);
        LocalTime hora = LocalTime.of(10, 0);
        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

        //Creamos una cita desde la sesion del paciente
        int citaId = cita.crearPorPaciente(sesionPac.pacienteId(), idDoc, fecha, fechaHora, "Chequeo general");

        //Formateamos la fecha con el patron HH:MM
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        //Creamos una lista de citas para el doctor
        var agenda = cita.agendaDoctor(idDoc, fecha);
        for (var c : agenda) {
            //Al imprimir formateamos la hora
            System.out.println("Agenda doctor: cita " + c.id + " hora " + c.hora.toLocalTime().format(formatoHora) + " estado " + c.estado);
        }

        //Actualizamos el estado de la cita a confirmada
        cita.actualizarEstado(citaId, EstadoCita.Pendiente);

        //Creamos una lista con las citas del paciente y le pasamos solo el día correspondiente a la cita como rango
        var citasPac = cita.citasPaciente(sesionPac.pacienteId(), fecha, fecha);
        for (var c : citasPac) {
            //Imprimimos las citas y formateamos la hora
            System.out.println("Citas paciente: " + c.id + " fecha " + c.fecha + " hora " + c.hora.toLocalTime().format(formatoHora) + " estado " + c.estado);
        }

        //Creamos una historia clinica
        int historiaId = historia.obtenerOCrearHistoria(sesionPac.pacienteId());

        //Agregamos una nota a esa historia clinica
        historia.agregarNota(historiaId, "Ninguna", "Ibuprofeno", "Dolor de cabeza", "Reposo");

        //Creamos una lista con las notas de historia clinica
        var notasH = historia.listarNotas(historiaId);
        for (var n : notasH) {
            System.out.println("Historia nota " + n.getId() +" Alergias: "+ n.getAlergias() +" Medicamentos: "+n.getMedicamentos()+" Motivo: " + n.getMotivoConsulta()+" Recomendaciones: "+n.getRecomendaciones());
        }

        //Creamos las notas del paciente
        int notaId = nota.ObtenerOCrearNota(sesionPac.pacienteId());

        //Agregamos una nota
        nota.agregarNota(notaId, "Nota personal: se siente mejor");

        //Creamos una lista que contenga las notas del paciente
        var notasN = nota.listarNotas(notaId);
        for (var n : notasN) {
            System.out.println("Nota simple " + n.getId() + ": " + n.getTexto());
        }

        //Cancelamos la cita
        cita.cancelarPorPaciente(citaId, sesionPac.pacienteId());
        System.out.println("Cita " + citaId + " cancelada");

        //Cerramos sesion
        autenticacion.logout(sesionPac.sesionId());
        System.out.println("Logout ok");
    }
}
