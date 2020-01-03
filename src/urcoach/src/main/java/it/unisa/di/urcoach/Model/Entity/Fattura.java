package it.unisa.di.urcoach.Model.Entity;

import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="Fatture")
public class Fattura {
    @Id
    @GeneratedValue
    @Column(name = "numeroFattura")
    private int numeroFattura;
    private float costo;
    private Date data = new Date(Calendar.getInstance().getTime().getTime());

    @ManyToOne
    @JoinColumn
    private Atleta atleta;

    //@OneToMany(fetch = FetchType.EAGER, mappedBy = "fattura")
    //private List<Acquisto> acquisti = new ArrayList<>();

    public Fattura() {
    }

    public Fattura(float costo, Atleta atleta) {
        this.costo = costo;
        this.atleta = atleta;
    }

    //public void aggiungiAcquisto(Pacchetto p) {
    //    Acquisto a = new Acquisto(this, p, p.getCosto());
    //    acquisti.add(a);
    //    p.getAcquisti().add(a);
    //}

    public int getNumeroFattura() {
        return numeroFattura;
    }

    public void setNumeroFattura(int numeroFattura) {
        this.numeroFattura = numeroFattura;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
