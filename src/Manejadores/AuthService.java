package Manejadores;

import Clases.Usuario;
import Memoria.UsuarioMem;

public class AuthService {
    //Creamos una clase LoginResult de tipo record para que contenga su propio constructor y métodos de acceso de forma resumida
    public record LoginResult(Usuario.User user, int sesionId, Integer pacienteId, Integer doctorId) {}

    // Creamos un usuarioMem llamado user
    UsuarioMem user = new UsuarioMem();

    //Creamos un metodo login que retorne LoginResult al ingresar como argumento Username y password
    public LoginResult login(String username, String password){
        //Verificamos que el usuario no esté en blanco o sea nulo y en caso de que la contraseña sea nula la dejamos en blanco
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Usuario requerido");
        if (password == null) password = "";

        //Creamos un usuario con base en el username que pasamos anteriormente
        var usuario = user.encontrarUsername(username);
        //Si el usuario es nulo quiere decir que no existe
        if (usuario == null) throw new IllegalArgumentException("Credenciales inválidas");
        //Verificamos que la cotraseña del usuario sea correcta
        if (!user.ContraC(username, password)) throw new IllegalArgumentException("Credenciales inválidas");

        //Ahora abrimos una sesion para el usuario y mantenemos en sesionId el número de sesion
        int sesionId = user.abrirSesion(usuario.getId());

        //Dependiendo del rol que tenga el usuario obtenemos en pacId o en docId él id correspondiente
        Integer pacId = user.pacienteIdPorUsuario(usuario.getId());
        Integer docId = user.doctorIdPorUsuario(usuario.getId());

        //Retornamos el nuevo LoginResult
        return new LoginResult(new Usuario.User(usuario.getId(), usuario.getUsername(), String.valueOf(usuario.getRol())),
                sesionId, pacId, docId);
    }

    //Pasamos él id de la sesion y cerramos sesion
    public void logout(int sesionId){
        user.cerrarSesion(sesionId);
    }
}
