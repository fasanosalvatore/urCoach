package it.unisa.di.urcoach.Model.Entity;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
public class Pacchetto {
    @Id
    @GeneratedValue
    private int idPacchetto;
    private String nome;
    private float costo;
    private int durata;
    private String foto;
    @ManyToOne
    @JoinColumn
    private Categoria categoria;
    private String descrizione;
    @ManyToOne
    @JoinColumn
    private PersonalTrainer personalTrainer;

    public Pacchetto() {
    }

    public Pacchetto(String nome, float costo, int durata, PersonalTrainer personalTrainer, String foto, Categoria categoria, String descrizione) {
        this.nome = nome;
        this.costo = costo;
        this.durata = durata;
        this.personalTrainer = personalTrainer;
        this.foto = foto;
        this.categoria = categoria;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
