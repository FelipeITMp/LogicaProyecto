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

    //Listamos la agenda del doctor en fechas especifícas
    @Override
    public List<Cita.AgendaItem> agendaDoctor(int IdDoctor, LocalDate fecha) {
        var doc = agendaPorDoctorYFecha.get(IdDoctor);
        if(doc ==null) return Collections.emptyList(); //Collections.empytlist retorna una lista vacia
        var citas = doc.getOrDefault(fecha, Collections.emptyList());
        //Con getorDefault retornamos la fecha en caso de que exista como key sino retornamos una lista vacia
        List<Cita.AgendaItem> listacitas = new ArrayList<>();

        for (Cita c : citas) { //Foreach para agregar citas
            listacitas.add(new Cita.AgendaItem(c.getId(),c.getHora(),c.getPacienteId(), c.getEstadoCita(), c.getObservacion()));
        }
        return listacitas;
    }

    //Creacion de citas
    @Override
    public int crear(int PacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estado, String observacion) {
        List<Cita> citasEnDia = agendaPorDoctorYFecha.computeIfAbsent(DoctorId, k -> new HashMap<>()).computeIfAbsent(fecha, k -> new ArrayList<>());
/* Con computeIfAbsent verificamos si existe él Id del doctor, y en caso de que no crea un hashmap,
en caso de que exista verifica que exista un hashmap con la fecha ingresada como llave y sinó crea una lista vacia*/

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
        //Comparamos y ordenamos las citas por la hora de forma ascendente

        // Guardar en citas del paciente
        citasPorPaciente.computeIfAbsent(PacienteId, k -> new TreeMap<>()).computeIfAbsent(fecha, k -> new ArrayList<>()).add(nueva);
        /*Verificamos que exista él id del paciente y en caso de que no creamos un TreeMap,
         en caso de que exista, verificamos si hay alguna fecha en el siguiente hashmap,
         y si no hay alguna fecha entonces creamos un arraylist*/

        return citaId;
    }
    
    //Creamos citas por los codigos
    @Override
    public int crearPorCodigos(int PacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, EstadoCita estado, String observacion) {
        return crear(PacienteId, DoctorId, fecha, hora, estado, observacion);
    }

    //Usamos ciclos for anidados para llegar al estado de la cita, y modificarlo por el que ingresemos
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

    // Listamos las citas de los pacientes
    @Override
    public List<Cita.CitaPacItem> citasPaciente(int pacienteid, LocalDate desde , LocalDate hasta) {
        //Usamos un NavigableMap porque permite ordenar de forma automatica
        NavigableMap<LocalDate, List<Cita>> citasPorFecha = citasPorPaciente.get(pacienteid);

        //En caso de que no haya una fecha de la cita entonces retornamos una lista vacia
        if (citasPorFecha == null) {
            return Collections.emptyList();
        }

        //Creamos un nuevo mapa que contenga unicamente las citas que tienen una fecha en el rango establecido
        NavigableMap<LocalDate, List<Cita>> subMapa = citasPorFecha.subMap(desde, true, hasta, true);
        List<Cita.CitaPacItem> items = new ArrayList<>();

        //Con foreach anidados obtenemos las citas y las retornamos
        for (List<Cita> citas : subMapa.values()) {
            for (Cita c : citas) {
                items.add(new Cita.CitaPacItem(c.getId(), c.getFecha(), c.getHora(), c.getDoctorId(), c.getEstadoCita(), c.getObservacion()
                ));
            }
        }
        return items;
    }

    //Creamos la cita desde la perspectiva del paciente
    @Override
    public int crearPorPaciente(int pacienteId, int DoctorId, LocalDate fecha, LocalDateTime hora, String observacion) {
        return crear(pacienteId, DoctorId, fecha, hora, EstadoCita.Pendiente, observacion);
    }

    //Cancelamos la cita desde la perspectiva del paciente
    @Override
    public void cancelarPorPaciente(int citaId, int pacienteId) {
        //Creamos un navigableMap que contenga las citas del paciente y las fechas
        NavigableMap<LocalDate, List<Cita>> citasPorFecha = citasPorPaciente.get(pacienteId);

        if (citasPorFecha == null) {
            throw new IllegalArgumentException("El paciente no tiene citas registradas");
        }

        //Recorremos las citas por fechas
        for (List<Cita> citas : citasPorFecha.values()) {
            for (Cita c : citas) {
                if (c.getId() == citaId) {
                    //Al encontrar la cita cambiamos su estado por cancelada
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
