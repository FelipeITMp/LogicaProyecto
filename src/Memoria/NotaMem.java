package Memoria;

import Clases.Notatxt;
import Contratos.INota;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotaMem implements INota {
    private static int autoid = 1;

    // Mapas para guardar información
    static Map<Integer, Integer> notaIdPorPaciente = new HashMap<>();
    static Map<Integer, Deque<Notatxt>> notasTxtPorNota = new HashMap<>();

    //Obtenemos una él id de una nota en caso de que exista sinó la creamos con una cola
    @Override
    public int ObtenerOCrearNota(int pacienteId){
        if(notaIdPorPaciente.containsKey(pacienteId)){
            return  notaIdPorPaciente.get(pacienteId);
        }else{
            int idNota = autoid++;
            notaIdPorPaciente.put(pacienteId, idNota);
            notasTxtPorNota.put(idNota, new ArrayDeque<>());
            return idNota;
        }
    }

    //Obtiene las notas anexadas a un paciente
    @Override
    public List<Notatxt> listarNotas(int notaId){
        // Corrección: validar el contenedor correcto
        if(!notasTxtPorNota.containsKey(notaId)){
            throw new IllegalArgumentException("No existen notas para mostrar");
        }
        return new ArrayList<>(notasTxtPorNota.get(notaId));
    }

    //Agregamos una nota para un paciente
    @Override
    public int agregarNota(int notaId, String texto){
        if(!notasTxtPorNota.containsKey(notaId)){
            throw new IllegalArgumentException("Debes crear primero el contenedor de notas para el paciente");
        }
        Notatxt notatxt = new Notatxt(notaId, texto);
        notasTxtPorNota.get(notaId).addFirst(notatxt);
        return notatxt.getId();
    }
}
