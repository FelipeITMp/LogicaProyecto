package Contratos;

import Clases.Doctor;
import java.util.List;

public interface IDoctor {
    List<Doctor.DoctorItem> listarTodos();
    Doctor.DoctorItem encontrarPorCodigo(String codigo);
}