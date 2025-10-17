package Manejadores;

import Clases.HistoriaNota;
import Memoria.HistoriaMem;

import java.util.List;

public class HistoriaService {
    // Creamos una constante e instanciamos HistoriaMem
    final HistoriaMem historiaRepo = new HistoriaMem();

    //LLamamos a los metodos de HistoriaMem
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
