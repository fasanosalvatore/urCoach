package it.unisa.di.urcoach.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PersonalTrainers")
public class PersonalTrainer {
    @Id
    @Email(message = "L'email non rispecchia il formato")
    private String email;
    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$", message = "Il codice fiscale non rispetta il formato")
    private String codiceFiscale;
    @Pattern(regexp = "^[A-Za-z ]{2,50}$", message = "Il nome non rispetta il formato")
    private String nome;
    @Pattern(regexp = "^[A-Za-z ]{2,50}$", message = "Il cognome non rispetta il formato")
    private String cognome;
    private Date dataNascita;
    @Pattern(regexp = "^[A-Za-z0-9]{5,15}$", message = "La password è troppo debole, inserire almeno 5 caratteri")
    private String password;
    @Pattern(regexp = "^[0-9]{11}$", message = "La partita IVA non rispetta il formato")
    private String pIva;
    @Pattern(regexp = "^.+\\.jpg$", message = "La foto deve essere in formato jpeg")
    private String foto;
    @Pattern(regexp = "^.+\\.pdf$", message = "Il curriculum deve essere in formato pdf")
    private String curriculum;
    private String bio;
    private int verificato;

    @JsonIgnore
    @OneToMany(mappedBy = "personalTrainer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<Pacchetto> pacchettiCreati = new ArrayList<>();

    public PersonalTrainer() {
    }

    public PersonalTrainer(String email, String codiceFiscale, String nome, String cognome, Date dataNascita, String password, String pIva, String bio, int verificato) {
        this.email = email;
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.password = password;
        this.pIva = pIva;
        //this.foto = foto;
        this.bio = bio;
        this.verificato = verificato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getpIva() {
        return pIva;
    }

    public void setpIva(String pIva) {
        this.pIva = pIva;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getVerificato() {
        return verificato;
    }

    public void setVerificato(int verificato) {
        this.verificato = verificato;
    }

    public List<Pacchetto> getPacchettiCreati() {
        return pacchettiCreati;
    }

    public void setPacchettiCreati(List<Pacchetto> pacchettiCreati) {
        this.pacchettiCreati = pacchettiCreati;
    }
}
