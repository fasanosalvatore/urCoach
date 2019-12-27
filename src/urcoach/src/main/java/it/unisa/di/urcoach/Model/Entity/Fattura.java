package it.unisa.di.urcoach.Model.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Fattura {
    @Id
    @GeneratedValue
    private int numeroFattura;
    private float costo;
    private int scontoCoupon;
    private String emailAtleta;
    private Date data;

    public Fattura() {
    }

    public Fattura(float costo, int scontoCoupon, String emailAtleta, Date data) {
        this.costo = costo;
        this.scontoCoupon = scontoCoupon;
        this.emailAtleta = emailAtleta;
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

    public String getEmailAtleta() {
        return emailAtleta;
    }

    public void setEmailAtleta(String emailAtleta) {
        this.emailAtleta = emailAtleta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
