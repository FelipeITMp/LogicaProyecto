package Contratos;

import Clases.Usuario;
import Enums.Genero;
import Enums.Rol;

import java.time.LocalDate;

public interface IUsuario {
    int crear(String username, String password, Rol rol, String cedula, String nombres, String apellidos, String correo,
              String telefono, Genero genero, String direccion, LocalDate fecha_nacimiento,
              String horario, String sede, String especialidad);
    Usuario encontrarUsername(String username);
    Usuario encontrarUsuarioPorId(int usuarioId);
    boolean ContraC(String username, String password);
    String ObtenerContra(String username);
    int abrirSesion(int usuarioId);
    void cerrarSesion(int sesionId);
    Integer pacienteIdPorUsuario(int usuarioId);
    Integer doctorIdPorUsuario(int usuarioId);
}
