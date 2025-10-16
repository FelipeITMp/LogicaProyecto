package Manejadores;

import Clases.HistoriaNota;
import Memoria.HistoriaMem;

import java.util.List;

public class HistoriaService {
    //abrir por código, agregar nota completa o “solo texto”
    final HistoriaMem historiaRepo = new HistoriaMem();

    public int obtenerOCrearHistoria(int pacienteId) {
        return historiaRepo.ObtenerOCrearHistoria(pacienteId);
    }

    public List<HistoriaNota> listarNotas(int historiaId) {
        return historiaRepo.listarNotas(historiaId);
    }

    public int agregarNota(int historiaId, String alergias, String medicamentos, String motivoConsulta, String recomendaciones) {
        return historiaRepo.agregarNota(historiaId, alergias, medicamentos, motivoConsulta, recomendaciones);
    }


}
