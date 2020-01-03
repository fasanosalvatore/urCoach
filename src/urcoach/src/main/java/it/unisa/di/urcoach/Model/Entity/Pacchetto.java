package it.unisa.di.urcoach.Model.Entity;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pacchetti")
public class Pacchetto {
    @Id
    @GeneratedValue
    private int idPacchetto;
    private String nome;
    private float costo;
    private int durata;
    private String foto;
    private Date dataCreazione;
    private String descrizione;

    @ManyToOne
    @JoinColumn
    private Categoria categoria;

    @ManyToOne
    @JoinColumn
    private PersonalTrainer personalTrainer;

    //@OneToMany(fetch = FetchType.EAGER, mappedBy = "pacchetto")
    //private List<Acquisto> acquisti = new ArrayList<>();

    public Pacchetto() {
    }

    public Pacchetto(String nome, float costo, int durata, PersonalTrainer personalTrainer, String foto, Categoria categoria, Date dataCreazione, String descrizione) {
        this.nome = nome;
        this.costo = costo;
        this.durata = durata;
        this.personalTrainer = personalTrainer;
        this.foto = foto;
        this.categoria = categoria;
        this.dataCreazione = dataCreazione;
        this.descrizione = descrizione;
    }

    public int getIdPacchetto() {
        return idPacchetto;
    }

    public void setIdPacchetto(int idPacchetto) {
        this.idPacchetto = idPacchetto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
