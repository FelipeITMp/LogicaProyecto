package Memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Clases.Doctor;
import Contratos.IDoctor;

public class DoctorMem implements IDoctor {
    // Inicialización mínima + estáticos para compartir datos entre instancias
    public static Map<String, Doctor> doctorPorCodigo = new HashMap<>();
    public List<Doctor> doctoresOrdenNombre = new ArrayList<>();

    public void agregarDoctor_Nombre(Doctor d){
        doctoresOrdenNombre.add(d);
        doctoresOrdenNombre.sort(Comparator.comparing(Doctor::getNombres));
    }

    public void agregarDoctores(Doctor d){
        doctorPorCodigo.put(d.getCedula(), d);
        agregarDoctor_Nombre(d);
    }

    @Override
    public Doctor.DoctorItem encontrarPorCodigo(String codigo){
        if(!doctorPorCodigo.containsKey(codigo)){
            throw new IllegalArgumentException("Doctor no encontrado");
        }else{
            var doctor = doctorPorCodigo.get(codigo);
            String nombrecomp = doctor.getNombres()+" "+doctor.getApellidos();
            return new Doctor.DoctorItem(doctor.getId(), nombrecomp, doctor.getEspecialidad());
        }
    }

    @Override
    public List<Doctor.DoctorItem> listarTodos(){
        ArrayList<Doctor.DoctorItem> doctores = new ArrayList<>();
        for(Doctor d: doctoresOrdenNombre){
            String nombrecomp = d.getNombres()+" "+d.getApellidos();
            doctores.add(new Doctor.DoctorItem(d.getId(), nombrecomp, d.getEspecialidad()));
        }
        return doctores;
    }
}
