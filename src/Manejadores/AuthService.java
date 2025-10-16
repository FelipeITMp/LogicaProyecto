package Manejadores;

import Clases.Usuario;
import Memoria.UsuarioMem;

public class AuthService {
    public record LoginResult(Usuario.User user, int sesionId, Integer pacienteId, Integer doctorId) {}

    // Mantengo la creación local, pero ahora los repos utilizan mapas estáticos compartidos
    UsuarioMem user = new UsuarioMem();

    public LoginResult login(String username, String password){
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Usuario requerido");
        if (password == null) password = "";

        var u = user.encontrarUsername(username);
        if (u == null) throw new IllegalArgumentException("Credenciales inválidas");
        if (!user.ContraC(username, password)) throw new IllegalArgumentException("Credenciales inválidas");

        int sesionId = user.abrirSesion(u.getId());

        // Evitar NPE devolviendo Integer que puede ser null
        Integer pacId = user.pacienteIdPorUsuario(u.getId());
        Integer docId = user.doctorIdPorUsuario(u.getId());

        return new LoginResult(new Usuario.User(u.getId(), u.getUsername(), String.valueOf(u.getRol())),
                sesionId, pacId, docId);
    }

    public void logout(int sesionId){
        user.cerrarSesion(sesionId);
    }
}
