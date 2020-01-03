package it.unisa.di.urcoach.Model.Entity;

import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "PersonalTrainers")
public class PersonalTrainer {
    @Id
    private String email;
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String password;
    private String pIva;
    private String foto;
    private String bio;
    private int verificato;
    /*
    @OneToMany(mappedBy = "personalTrainer")
    List<Pacchetto> pacchettiCreati;
    */

    public PersonalTrainer() {
    }

    public PersonalTrainer(String email, String codiceFiscale, String nome, String cognome, Date dataNascita, String password, String pIva, MultipartFile foto, String bio, int verificato) {
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
}
