package Memoria;

import Clases.Cita;
import Contratos.ICita;
import Enums.EstadoCita;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

//Implementacion de interfaz
public class CitaMem implements ICita {
    //Mapas para guardar en memoria
    static Map<Integer, Map<LocalDate, List<Cita>>> agendaPorDoctorYFecha = new HashMap<>();
    static Map<Integer, NavigableMap<LocalDate, List<Cita>>> citasPorPaciente = new HashMap<>();

    //Listamos la agenda del doctor en fechas especificas
    @Override
    public List<Cita.AgendaItem> agendaDoctor(int IdDoctor, LocalDate fecha) {
        var doc = agendaPorDoctorYFecha.get(IdDoctor);
        if(doc ==null) return Collections.emptyList(); //Collections.empytlist retorna una lista vacia
        var citas = doc.getOrDefault(fecha, Collections.emptyList());
        //Con getorDefault retornamos la fecha en caso de que exista como key sino retornamos una lista vacia
        List<Cita.AgendaItem> listacitas = new ArrayList<>();

        for (Cita c : citas) { //Foreach
            listacitas.add(new Cita.AgendaItem(c.getId(),c.getHora(),c.getPacienteId(), c.getEstadoCita(), c.getObservacion()));
        }
        return listacitas;
    }

    //Creacion de citas
    @Override
    public int crear(int PacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estado, String observacion) {
        List<Cita> citasEnDia = agendaPorDoctorYFecha.computeIfAbsent(DoctorId, k -> new HashMap<>()).computeIfAbsent(fecha, k -> new ArrayList<>());
/* Con computeIfAbsent verificamos si existe el Id del doctor, y en caso de que no crea un hashmap,
en caso de que exista verifica que exista un hashmap con la fecha ingresada como llave y sino crea una lista vacia*/

        //foreach para recorrer las citas
        for (Cita c : citasEnDia) {
            if (c.getHora().equals(hora)) {
                throw new IllegalArgumentException("Ya existe una cita para ese doctor en la misma hora");
            }
        }

        Cita nueva = new Cita(PacienteId, DoctorId, fecha, hora, estado, observacion);
        int citaId = nueva.getId();


        citasEnDia.add(nueva);
        citasEnDia.sort(Comparator.comparing(Cita::getHora));

        // Guardar en citas del paciente
        citasPorPaciente.computeIfAbsent(PacienteId, k -> new TreeMap<>()).computeIfAbsent(fecha, k -> new ArrayList<>()).add(nueva);

        return citaId;
    }

    @Override
    public int crearPorCodigos(int PacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estado, String observacion) {
        return crear(PacienteId, DoctorId, fecha, hora, estado, observacion);
    }

    @Override
    public void actualizarEstado(int citaId, EstadoCita Nuevoestado) {
        for (Map<LocalDate, List<Cita>> agenda : agendaPorDoctorYFecha.values()) {
            for (List<Cita> citas : agenda.values()) {
                for (Cita c : citas) {
                    if (c.getId() == citaId) {
                        c.setEstadoCita(Nuevoestado);
                        return;
                    }
                }
            }
        }
        throw new IllegalArgumentException("No existe cita con id " + citaId);
    }

    @Override
    public List<Cita.CitaPacItem> citasPaciente(int pacienteid, LocalDate desde , LocalDate hasta) {
        NavigableMap<LocalDate, List<Cita>> citasPorFecha = citasPorPaciente.get(pacienteid);
        if (citasPorFecha == null) {
            return Collections.emptyList();
        }

        NavigableMap<LocalDate, List<Cita>> subMapa = citasPorFecha.subMap(desde, true, hasta, true);
        List<Cita.CitaPacItem> items = new ArrayList<>();

        for (List<Cita> citas : subMapa.values()) {
            for (Cita c : citas) {
                items.add(new Cita.CitaPacItem(c.getId(), c.getFecha(), c.getHora(), c.getDoctorId(), c.getEstadoCita(), c.getObservacion()
                ));
            }
        }
        return items;
    }

    @Override
    public int crearPorPaciente(int pacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, String observacion) {
        return crear(pacienteId, DoctorId, fecha, hora, EstadoCita.Pendiente, observacion);
    }

    @Override
    public void cancelarPorPaciente(int citaId, int pacienteId) {
        NavigableMap<LocalDate, List<Cita>> citasPorFecha = citasPorPaciente.get(pacienteId);
        if (citasPorFecha == null) {
            throw new IllegalArgumentException("El paciente no tiene citas registradas");
        }
        for (List<Cita> citas : citasPorFecha.values()) {
            for (Cita c : citas) {
                if (c.getId() == citaId) {
                    if (c.getEstadoCita() == EstadoCita.Pendiente || c.getEstadoCita() == EstadoCita.Confirmada) {
                        c.setEstadoCita(EstadoCita.Cancelada);
                        return;
                    } else {
                        throw new IllegalStateException("La cita no puede cancelarse en su estado actual");
                    }
                }
            }
        }
        throw new IllegalArgumentException("La cita no pertenece al paciente " + pacienteId);
    }
}
