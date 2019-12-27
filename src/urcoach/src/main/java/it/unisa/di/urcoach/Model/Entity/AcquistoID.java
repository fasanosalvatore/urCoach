package it.unisa.di.urcoach.Model.Entity;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class AcquistoID implements Serializable {
    @ManyToOne
    @JoinColumn
    private Fattura fattura;
    private int idPacchetto;

    public AcquistoID() {
    }

    public AcquistoID(Fattura fattura, int idPacchetto) {
        this.fattura = fattura;
        this.idPacchetto = idPacchetto;
    }

    public int getIdPacchetto() {
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        this.idPacchetto = idPacchetto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcquistoID that = (AcquistoID) o;

        if (fattura.getNumeroFattura() != that.fattura.getNumeroFattura()) return false;
        return idPacchetto == that.idPacchetto;
    }

    @Override
    public int hashCode() {
        int result = fattura.hashCode();
        result = 31 * result + fattura.hashCode();
        return result;
    }
}
