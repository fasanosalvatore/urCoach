package it.unisa.di.urcoach.Model.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Fattura {
    @Id
    @GeneratedValue
    private int numeroFattura;
    private float costo;
    private int scontoCoupon;
    private Date data;

    @OneToMany(mappedBy = "fattura")
    private List<Acquisto> acquisti;

    @ManyToOne
    @JoinColumn
    private Atleta atleta;

    public Fattura() {
    }

    public Fattura(float costo, int scontoCoupon, Date data, Atleta atleta) {
        this.costo = costo;
        this.scontoCoupon = scontoCoupon;
        this.atleta = atleta;
        this.data = data;
    }

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

    public int getScontoCoupon() {
        return scontoCoupon;
    }

    public void setScontoCoupon(int scontoCoupon) {
        this.scontoCoupon = scontoCoupon;
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
