package Memoria;

import java.util.*;

import Clases.Doctor;
import Contratos.IDoctor;

public class DoctorMem implements IDoctor {
    // Mapa y Array para guardar en memoria
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

    //Usamos los metodos contenidos en las interfaces

    //Buscamos los doctores por codigo y los devolvemos como DoctorItem
    @Override
    public Doctor.DoctorItem encontrarPorCodigo(String codigo){
        var doctor = doctorPorCodigo.get(codigo);

        if(doctor == null){
            throw new IllegalArgumentException("Doctor no encontrado");
        }

        String nombrecomp = doctor.getNombres()+" "+doctor.getApellidos();
        return new Doctor.DoctorItem(doctor.getId(), nombrecomp, doctor.getEspecialidad());

    }

    //Listamos todos los doctores de forma ordenada por el nombre
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
