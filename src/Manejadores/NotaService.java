package Manejadores;

import Clases.Notatxt;
import Memoria.NotaMem;

import java.util.List;

public class NotaService {
    // Creamos una constante e instanciamos NotaMem
    final NotaMem nota = new NotaMem();

    //Llamamos a los metodos de nota
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
