package Manejadores;

import Clases.Notatxt;
import Memoria.NotaMem;

import java.util.List;

public class NotaService {
//abrir por código (getOrCreate), agregar texto y listar
    final NotaMem nota = new NotaMem();

    public int ObtenerOCrearNota(int pacienteId){
        return nota.ObtenerOCrearNota(pacienteId);
    }

    public List<Notatxt> listarNotas(int notaId){
        return nota.listarNotas(notaId);
    }

    public int agregarNota(int notaId,String texto){
        return nota.agregarNota(notaId,texto);
    }

}
