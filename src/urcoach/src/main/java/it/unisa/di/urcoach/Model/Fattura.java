package it.unisa.di.urcoach.Model;

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


}
