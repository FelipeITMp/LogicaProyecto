package Clases;

public class HistoriaClinica    {
    private static int autoId=1;
    private int id;
    private int idPaciente;

    //Metodo constructor de la historia clinica
    public HistoriaClinica(int id, int idPaciente) {
        this.id = autoId++;
        this.idPaciente = idPaciente;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
