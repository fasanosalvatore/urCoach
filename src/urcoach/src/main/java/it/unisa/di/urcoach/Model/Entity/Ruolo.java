package it.unisa.di.urcoach.Model.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Ruolo {
    @Id
    @GeneratedValue
    private int idRuolo;
    private String nome;

    @ManyToMany(mappedBy = "ruoli")
    private List<Amministrativo> amministrativi;
}
