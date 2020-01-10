package it.unisa.di.urcoach.model.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AcquistoID implements Serializable {

    private int numeroFattura;
    private int idPacchetto;

    public AcquistoID() {
    }

    public AcquistoID(int numeroFattura, int idPacchetto) {
        this.numeroFattura = numeroFattura;
        this.idPacchetto = idPacchetto;
    }

    public int getNumeroFattura() {
        return numeroFattura;
    }

    public void setNumeroFattura(int numeroFattura) {
        this.numeroFattura = numeroFattura;
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

        if (numeroFattura != that.numeroFattura) return false;
        return idPacchetto == that.idPacchetto;
    }

    @Override
    public int hashCode() {
        int result = ((Integer) numeroFattura).hashCode();
        result = 31 * result + ((Integer) idPacchetto).hashCode();
        return result;
    }
}
