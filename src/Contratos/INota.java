package Contratos;

import Clases.Notatxt;
import java.util.List;

public interface INota {
    int ObtenerOCrearNota(int pacienteId);   // antes decías "Historia"
    List<Notatxt> listarNotas(int notaId);
    int agregarNota(int notaId, String texto);
}

