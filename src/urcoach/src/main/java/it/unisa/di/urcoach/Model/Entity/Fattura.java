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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numeroFattura")
    private int numeroFattura;
    private float costo;
    private Date data = new Date(Calendar.getInstance().getTime().getTime());

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn
    private Atleta atleta;

    @OneToMany(mappedBy = "fattura", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Acquisto> acquisti;

    public Fattura() {
    }

    public Fattura(float costo, Atleta atleta) {
        this.costo = costo;
        this.atleta = atleta;
    }

    public Fattura(float costo, Atleta atleta, Date data) {
        this.costo = costo;
        this.atleta = atleta;
        this.data = data;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fattura that = (Fattura) o;

        return (numeroFattura == that.getNumeroFattura());
    }
}
