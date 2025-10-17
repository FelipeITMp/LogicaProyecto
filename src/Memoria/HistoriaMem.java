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

    //Maps para guardar la información
    static Map<Integer, Integer> historiaPorPaciente = new HashMap<>();
    static Map<Integer, Deque<HistoriaNota>> notasClinicasPorHistoria = new HashMap<>();

    //Obtenemos la historia que estemos buscando, y en caso de no encontrarla creamos una
    @Override
    public int ObtenerOCrearHistoria(int pacienteId){
        //En caso de encontrarla pasa él id de la historia
        if(historiaPorPaciente.containsKey(pacienteId)){
            return  historiaPorPaciente.get(pacienteId);
        }else{
            //Sinó crea una historia con una cola adentro
            int idhistoria = autoid++;
            historiaPorPaciente.put(pacienteId, idhistoria);
            notasClinicasPorHistoria.put(idhistoria, new ArrayDeque<>());
            return idhistoria;
        }
    }

    //Obtenemos todas las notas de una historia especifíca como una lista
    @Override
    public List<HistoriaNota> listarNotas(int historiaId){
        if(!notasClinicasPorHistoria.containsKey(historiaId)){
            throw new IllegalArgumentException("La historia no existe");
        }
        return new ArrayList<>(notasClinicasPorHistoria.get(historiaId));
    }

    //Agregamos una nota
    @Override
    public int agregarNota(int historiaId, String alergias, String medicamentos, String motivoconsulta, String recomendaciones){
        HistoriaNota historiaNota = new HistoriaNota(historiaId, alergias, medicamentos, motivoconsulta, recomendaciones);
        notasClinicasPorHistoria.get(historiaId).addFirst(historiaNota);
        return historiaNota.getId();
    }
}
