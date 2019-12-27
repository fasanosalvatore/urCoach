package it.unisa.di.urcoach.Model.Entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Acquisto {
    @EmbeddedId
    private AcquistoID acquistoId;
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
