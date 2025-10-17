package Memoria;

import Clases.Doctor;
import Clases.Paciente;
import Clases.Usuario;
import Clases.Sesion;
import Contratos.IUsuario;
import Enums.EstadoSesion;
import Enums.Rol;
import Enums.Genero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsuarioMem implements IUsuario {
    //Mapas y un array para guardar las sesiones y usuarios
    static Map<String, Usuario> usuariosPorUsername = new HashMap<>();
    static Map<Integer, Usuario> usuariosPorId = new HashMap<>();
    static ArrayList<Sesion> historialSesiones = new ArrayList<>();


    //Creamos un usuario con todos los datos disponibles péro dependiendo del rol usamos unos u otros
    @Override
    public int crear(String username, String password, Rol rol, String cedula, String nombres, String apellidos,
                     String correo, String telefono, Genero genero, String direccion, LocalDate fecha_nacimiento,
                     String horario, String sede, String especialidad) {

        Usuario usuario = new Usuario(username, password, rol); //Creamos un nuevo usuario
        usuariosPorId.put(usuario.getId(), usuario); //Agregamos el usuario por su Id
        usuariosPorUsername.put(username, usuario); //Agregamos el usuario por su Username

        //Dependiendo del rol agregamos o un paciente o un doctor
        if (rol == Rol.Paciente) {
            agregarPaciente(cedula, nombres, apellidos, correo, telefono, genero, direccion, fecha_nacimiento, usuario.getId());
        }
        if (rol == Rol.Doctor) {
            agregarDoctor(cedula, nombres, apellidos, correo, telefono, genero, usuario.getId(), horario, sede, especialidad);
        }
        //Retornamos él id del usuario creado
        return usuario.getId();
    }

    //Buscamos un usuario por su id y en caso de encontrarlo lo retornamos como objeto
    @Override
    public Usuario encontrarUsuarioPorId(int usuarioId) {
        if (!usuariosPorId.containsKey(usuarioId)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        } else {
            return usuariosPorId.get(usuarioId);
        }
    }

    //Buscamos un usuario por su username y en caso de encontrarlo lo retornamos como objeto
    @Override
    public Usuario encontrarUsername(String username) {
        if (!usuariosPorUsername.containsKey(username)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        } else {
            return usuariosPorUsername.get(username);
        }
    }

    //Verificamos que la contraseña sea correcta
    @Override
    public boolean ContraC(String username, String password) {
        var userE = encontrarUsername(username);
        return userE.getPassword().equals(password);
    }

    //Obtenemos la contraseña en texto plano
    @Override
    public String ObtenerContra(String username) {
        return encontrarUsername(username).getPassword();
    }

    //Abrimos una sesion con él id del usuario y retornamos él id de la sesion
    @Override
    public int abrirSesion(int usuarioId) {
        var sesion = new Sesion(usuarioId);
        historialSesiones.add(sesion);
        return sesion.getId();
    }

    //Cerramos la sesion por él Id sesion y cambiamos su estado a Cerrada
    @Override
    public void cerrarSesion(int SesionId) {
        for (Sesion s : historialSesiones) {
            if (s.getId() == SesionId && s.getEstadoSesion() == EstadoSesion.Activa) {
                s.setEstadoSesion(EstadoSesion.Cerrada);
                return;
            }
        }
        throw new IllegalStateException("No hay sesión activa para el usuario " + SesionId);
    }

    //Buscamos él id del paciente por él id del usuario
    @Override
    public Integer pacienteIdPorUsuario(int usuarioId) {
        for (Paciente p : PacienteMem.pacientePorCodigo.values()) {
            if (p.getUsuarioId() == usuarioId)
                return p.getId();
        }
        return null;
    }

    //Buscamos él id del doctor por él id del usuario
    @Override
    public Integer doctorIdPorUsuario(int usuarioId) {
        for (Doctor d : DoctorMem.doctorPorCodigo.values()) {
            if (d.getUsuarioId() == usuarioId)
                return d.getId();
        }
        return null;
    }

    //Funcion interna para agregarPaciente
    public void agregarPaciente(String cedula, String nombres, String apellidos, String correo, String telefono,
                                Genero genero, String direccion, LocalDate fecha_nacimiento, int usuarioId) {

        PacienteMem.pacientePorCodigo.put(cedula, new Paciente(cedula
                , nombres, apellidos, correo, telefono, genero, direccion, fecha_nacimiento, usuarioId));
    }

    //Funcion interna para agregar doctor
    public void agregarDoctor(String cedula, String nombres, String apellidos, String correo, String telefono,
                              Genero genero, int usuarioId, String horario, String sede, String especialidad) {

        DoctorMem.doctorPorCodigo.put(cedula,
                new Doctor(cedula, nombres, apellidos, correo, telefono, genero, horario, sede, especialidad, usuarioId));
    }
}
