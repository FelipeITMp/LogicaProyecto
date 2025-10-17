package Memoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Clases.Paciente;
import Contratos.IPaciente;

public class PacienteMem implements IPaciente {
    //Mapas para guardar info
    static Map<String, Paciente> pacientePorCodigo = new HashMap<>();
    TreeMap<String, Paciente> PacientesPorNombre = new TreeMap<>();

    //Agregamos pacientes por nombre
    public void AgregarPacientesPorNombre(){
        for(Paciente p : pacientePorCodigo.values()){
            PacientesPorNombre.put(p.getNombres().trim().toLowerCase(), p);
        }
    }

    //Buscamos pacientes por su codigo
    @Override
    public Paciente.PacienteItem encontrarPorCodigo(String codigo){
        if(!pacientePorCodigo.containsKey(codigo)){
            throw new IllegalArgumentException("Paciente no encontrado");
        }else{
            var paciente = pacientePorCodigo.get(codigo);
            return new Paciente.PacienteItem(paciente.getId(), paciente.getCedula(), paciente.getNombres());
        }
    }

    //Buscamos pacientes por nombre
    @Override
    public List<Paciente.PacienteItem> buscarPorNombre(String filtro){
        List<Paciente.PacienteItem> listp = new ArrayList<>();
        String filtroNormalizado = filtro.trim().toLowerCase();

        for(Paciente p: PacientesPorNombre.values()){
            if(p.getNombres().trim().toLowerCase().startsWith(filtroNormalizado)){
                listp.add(new Paciente.PacienteItem(p.getId(), p.getCedula(), p.getNombres()));
            }
        }
        return listp;
    }
}
