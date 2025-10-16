package Contratos;

import Clases.Paciente;
import java.util.List;

public interface IPaciente {
    Paciente.PacienteItem encontrarPorCodigo(String codigo);
    List<Paciente.PacienteItem> buscarPorNombre(String filtro);
}

