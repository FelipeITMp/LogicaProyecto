package Memoria;

import Clases.HistoriaNota;
import Contratos.IHistoria;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoriaMem implements IHistoria {
    private static int autoid = 1;

    // Inicialización mínima + estáticos para compartir datos
    static Map<Integer, Integer> historiaPorPaciente = new HashMap<>();
    static Map<Integer, Deque<HistoriaNota>> notasClinicasPorHistoria = new HashMap<>();

    @Override
    public int ObtenerOCrearHistoria(int pacienteId){
        if(historiaPorPaciente.containsKey(pacienteId)){
            return  historiaPorPaciente.get(pacienteId);
        }else{
            int idhistoria = autoid++;
            historiaPorPaciente.put(pacienteId, idhistoria);
            notasClinicasPorHistoria.put(idhistoria, new ArrayDeque<>());
            return idhistoria;
        }
    }

    @Override
    public List<HistoriaNota> listarNotas(int historiaId){
        if(!notasClinicasPorHistoria.containsKey(historiaId)){
            throw new IllegalArgumentException("La historia no existe");
        }
        return new ArrayList<>(notasClinicasPorHistoria.get(historiaId));
    }

    @Override
    public int agregarNota(int historiaId, String alergias, String medicamentos, String motivoconsulta, String recomendaciones){
        HistoriaNota historiaNota = new HistoriaNota(historiaId, alergias, medicamentos, motivoconsulta, recomendaciones);
        notasClinicasPorHistoria.get(historiaId).addFirst(historiaNota);
        return historiaNota.getId();
    }
}
