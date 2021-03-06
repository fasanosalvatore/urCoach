package it.unisa.di.urcoach.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "Acquisti")
public class Acquisto {
    @EmbeddedId
    private AcquistoID acquistoId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("numeroFattura")
    @JoinColumn
    private Fattura fattura;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("idPacchetto")
    @JoinColumn
    private Pacchetto pacchetto;

    private float costo;

    public Acquisto(Fattura fattura, Pacchetto pacchetto, float costo) {
        this.fattura = fattura;
        this.pacchetto = pacchetto;
        this.costo = costo;
        this.acquistoId = new AcquistoID(fattura.getNumeroFattura(), pacchetto.getIdPacchetto());
    }

    public Acquisto() {

    }

    public AcquistoID getAcquistoId() {
        return acquistoId;
    }

    public void setAcquistoId(AcquistoID acquistoId) {
        this.acquistoId = acquistoId;
    }

    public Fattura getFattura() {
        return fattura;
    }

    public void setFattura(Fattura fattura) {
        this.fattura = fattura;
    }

    public Pacchetto getPacchetto() {
        return pacchetto;
    }

    public void setPacchetto(Pacchetto pacchetto) {
        this.pacchetto = pacchetto;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
