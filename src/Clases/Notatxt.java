package Clases;

import java.time.LocalDate;

public class Notatxt {
    //Objeto nota
    private static int autId = 1;
    private int id;
    private int notaId;
    private String texto;
    private LocalDate creada;

    public Notatxt(int notaId, String texto) {
        this.id = autId++;
        this.notaId = notaId;
        this.texto = texto;
        creada = LocalDate.now();
    }

    public LocalDate getCreada() {
        return creada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotaId() {
        return notaId;
    }

    public void setNotaId(int notaId) {
        this.notaId = notaId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
