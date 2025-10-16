package Clases;


import Enums.Rol;

public class Usuario {
    private static int autoId = 1;
    private int id;
    private String username;
    private String password;
    private Rol rol;

    //Clase anidada para mostrar el paciente
    public static final class User {
        public final int id;
        public final String username;
        public final String rol;

        public User(int id, String username, String rol) {
            this.id = id;
            this.username = username;
            this.rol = rol;
        }
    }

    //Metodo constructor Usuario
    public Usuario(String username, String password, Rol rol) {
        this.id = autoId++;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

