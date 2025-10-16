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
    static Map<String, Usuario> usuariosPorUsername = new HashMap<>();
    static Map<Integer, Usuario> usuariosPorId = new HashMap<>();
    static ArrayList<Sesion> historialSesiones = new ArrayList<>();


    @Override
    public int crear(String username, String password, Rol rol, String cedula, String nombres, String apellidos,
                     String correo, String telefono, Genero genero, String direccion, LocalDate fecha_nacimiento,
                     String horario, String sede, String especialidad) {

        Usuario usuario = new Usuario(username, password, rol);
        usuariosPorId.put(usuario.getId(), usuario);
        usuariosPorUsername.put(username, usuario);

        if (rol == Rol.Paciente) {
            agregarPaciente(cedula, nombres, apellidos, correo, telefono, genero, direccion, fecha_nacimiento, usuario.getId());
        }
        if (rol == Rol.Doctor) {
            agregarDoctor(cedula, nombres, apellidos, correo, telefono, genero, usuario.getId(), horario, sede, especialidad);
        }

        return usuario.getId();
    }

    @Override
    public Usuario encontrarUsuarioPorId(int usuarioId) {
        if (!usuariosPorId.containsKey(usuarioId)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        } else {
            return usuariosPorId.get(usuarioId);
        }
    }

    @Override
    public Usuario encontrarUsername(String username) {
        if (!usuariosPorUsername.containsKey(username)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        } else {
            return usuariosPorUsername.get(username);
        }
    }

    @Override
    public boolean ContraC(String username, String password) {
        var userE = encontrarUsername(username);
        return userE.getPassword().equals(password);
    }

    @Override
    public String ObtenerContra(String username) {
        return encontrarUsername(username).getPassword();
    }

    @Override
    public int abrirSesion(int usuarioId) {
        var sesion = new Sesion(usuarioId);
        historialSesiones.add(sesion);
        return sesion.getId();
    }

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

    @Override
    public Integer pacienteIdPorUsuario(int usuarioId) {
        for (Paciente p : PacienteMem.pacientePorCodigo.values()) {
            if (p.getUsuarioId() == usuarioId)
                return p.getId();
        }
        return null;
    }

    @Override
    public Integer doctorIdPorUsuario(int usuarioId) {
        for (Doctor d : DoctorMem.doctorPorCodigo.values()) {
            if (d.getUsuarioId() == usuarioId)
                return d.getId();
        }
        return null;
    }

    public void agregarPaciente(String cedula, String nombres, String apellidos, String correo, String telefono,
                                Genero genero, String direccion, LocalDate fecha_nacimiento, int usuarioId) {
        PacienteMem.pacientePorCodigo.put(cedula,
                new Paciente(cedula, nombres, apellidos, correo, telefono, genero, direccion, fecha_nacimiento, usuarioId));
    }

    public void agregarDoctor(String cedula, String nombres, String apellidos, String correo, String telefono,
                              Genero genero, int usuarioId, String horario, String sede, String especialidad) {
        DoctorMem.doctorPorCodigo.put(cedula,
                new Doctor(cedula, nombres, apellidos, correo, telefono, genero, horario, sede, especialidad, usuarioId));
    }
}
