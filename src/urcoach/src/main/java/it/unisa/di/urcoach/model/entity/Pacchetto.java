package it.unisa.di.urcoach.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pacchetti")
public class Pacchetto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPacchetto;
    @Pattern(regexp = "^[A-Za-z ]{2,50}$", message = "Il nome ha un formato non valido")
    private String nome;
    @DecimalMax(value = "999.99", message = "Il costo ha un formato non valido")
    private float costo;
    @DecimalMin(value = "1", message = "La durata ha un formato non valido, ricorda un pacchetto può durare max 12 mesi")
    @DecimalMax(value = "12", message = "La durata ha un formato non valido, ricorda un pacchetto può durare max 12 mesi")
    private int durata;
    private String foto;
    @Pattern(regexp = "\\b(\\w*Base|Intermedio|Avanazato\\w*)\\b", message = "La difficoltà non è valida")
    private String difficolta;
    private Date dataCreazione;
    @Pattern(regexp = "^[A-Za-z ]{0,200}$", message = "La descrizione ha un formato non valido")
    private String descrizione;

    @ManyToOne
    @JoinColumn
    private Categoria categoria;


    @ManyToOne
    @JoinColumn
    private PersonalTrainer personalTrainer;

    @JsonIgnore
    @OneToMany(mappedBy = "pacchetto", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Acquisto> acquisti = new ArrayList<>();

    public Pacchetto() {
    }

    public Pacchetto(String nome, float costo, int durata, Date dataCreazione, String descrizione) {
        this.nome = nome;
        this.costo = costo;
        this.durata = durata;
        this.dataCreazione = dataCreazione;
        this.descrizione = descrizione;
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

    public String getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(String difficolta) {
        this.difficolta = difficolta;
    }

    public List<Acquisto> getAcquisti() {
        return acquisti;
    }

    public void setAcquisti(List<Acquisto> acquisti) {
        this.acquisti = acquisti;
    }

}
