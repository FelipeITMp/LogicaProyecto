package Clases;

import java.time.LocalDate;

public class HistoriaNota {
    private static int autoId = 1;
    private int id;
    private int historiaId;
    private String alergias;
    private String medicamentos;
    private String motivoConsulta;
    private String recomendaciones;
    private LocalDate creada;

    //Metodo constructor de la nota de la historia clinica
    public HistoriaNota( int historiaId, String alergias, String medicamentos, String motivoConsulta, String recomendaciones) {
        this.id = autoId++;
        this.historiaId = historiaId;
        this.alergias = alergias;
        this.medicamentos = medicamentos;
        this.motivoConsulta = motivoConsulta;
        this.recomendaciones = recomendaciones;
        creada= LocalDate.now();

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

    public int getHistoriaId() {
        return historiaId;
    }

    public void setHistoriaId(int historiaId) {
        this.historiaId = historiaId;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
}
