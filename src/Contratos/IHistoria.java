package Contratos;

import Clases.HistoriaNota;
import java.util.List;

public interface IHistoria {
    int ObtenerOCrearHistoria(int pacienteId);
    List<HistoriaNota> listarNotas(int historiaId);
    int agregarNota(int historiaId, String alergias, String medicamentos, String motivoConsulta, String recomendaciones);
}

