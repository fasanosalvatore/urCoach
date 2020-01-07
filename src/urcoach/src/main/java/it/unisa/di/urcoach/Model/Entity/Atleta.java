package it.unisa.di.urcoach.Model.Entity;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Atleti")
public class Atleta {

    @Id
    @Column(name = "email")
    @Email(message = "L'email non rispecchia il formato")
    private String email;
    @Pattern(regexp = "^[A-Za-z ]{2,50}$", message = "Il nome non rispetta il formato")
    private String nome;
    @Pattern(regexp = "^[A-Za-z ]{2,50}$", message = "Il cognome non rispetta il formato")
    private String cognome;
    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$", message = "Il codice fiscale non rispetta il formato")
    private String codiceFiscale;
    private Date dataNascita;
    @Pattern(regexp = "^[A-Za-z ][0-9]{6,50}$", message = "L'indirizzo di fatturazione non rispetta il formato")
    private String indirizzoFatturazione;
    @Pattern(regexp = "^[A-Za-z0-9]{5,15}$", message = "La password è troppo debole, inserire almeno 5 caratteri")
    private String password;
    @OneToMany(mappedBy = "atleta", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Fattura> fatture = new ArrayList<>();

    public Atleta() {
    }

    public Atleta(String email, String nome, String cognome, String codiceFiscale, Date dataNascita, String indirizzoFatturazione, String password) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.dataNascita = dataNascita;
        this.indirizzoFatturazione = indirizzoFatturazione;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getIndirizzoFatturazione() {
        return indirizzoFatturazione;
    }

    public void setIndirizzoFatturazione(String indirizzoFatturazione) {
        this.indirizzoFatturazione = indirizzoFatturazione;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public List<Fattura> getFatture() {
        return fatture;
    }

    public void setFatture(List<Fattura> fatture) {
        this.fatture = fatture;
    }
}
