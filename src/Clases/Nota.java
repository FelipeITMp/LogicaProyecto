package Clases;

public class Nota {
    private static int autoId = 1;
    private int id;
    private int idPaciente;

    public Nota(int idPaciente) {
        this.id = autoId++;
        this.idPaciente = idPaciente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
}
