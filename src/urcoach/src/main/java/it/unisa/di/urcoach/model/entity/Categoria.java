package it.unisa.di.urcoach.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "Categorie")
public class Categoria {
    @Id
    @Pattern(regexp = "\\b(\\w*Massa|Definizione|Mantenimento\\w*)\\b", message = "La categoria richiesta non è presente")
    private String nome;

    private String descrizione;

    public Categoria() {
    }

    public Categoria(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
