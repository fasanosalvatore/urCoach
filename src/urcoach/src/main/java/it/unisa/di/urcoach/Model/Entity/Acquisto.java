package it.unisa.di.urcoach.Model.Entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Acquisti")
public class Acquisto {
    @EmbeddedId
    private AcquistoID acquistoId;

    @ManyToOne
    @MapsId("numeroFattura")
    @JoinColumn
    private Fattura fattura;

    @ManyToOne
    @MapsId("idPacchetto")
    @JoinColumn
    private Pacchetto pacchetto;

    private String nomePacchetto;
    private float costo;

    public Acquisto() {
    }

    public Acquisto(AcquistoID acquistoId, String nomePacchetto, float costo) {
        this.acquistoId = acquistoId;
        this.nomePacchetto = nomePacchetto;
        this.costo = costo;
    }

    public AcquistoID getAcquistoId() {
        return acquistoId;
    }

    public void setAcquistoId(AcquistoID acquistoId) {
        this.acquistoId = acquistoId;
    }

    public String getNomePacchetto() {
        return nomePacchetto;
    }

    public void setNomePacchetto(String nomePacchetto) {
        this.nomePacchetto = nomePacchetto;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
