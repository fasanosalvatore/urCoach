package it.unisa.di.urcoach.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Acquisto {
    @Id
    private int numeroFattura;
    @Id
    private int idPacchetto;
    private String nomePacchetto;
    private float costo;

    public Acquisto() {
    }

    public Acquisto(int numeroFattura, int idPacchetto, String nomePacchetto, float costo) {
        this.numeroFattura = numeroFattura;
        this.idPacchetto = idPacchetto;
        this.nomePacchetto = nomePacchetto;
        this.costo = costo;
    }
}
