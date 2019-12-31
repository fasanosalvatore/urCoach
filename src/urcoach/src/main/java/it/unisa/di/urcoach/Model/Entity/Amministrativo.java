package it.unisa.di.urcoach.Model.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Amministrativi")
public class Amministrativo {
    @Id
    private String email;
    private String nome;
    private String cognome;
    private String password;

    @ManyToMany
    @JoinTable(name = "ruoloAssegnato",
            joinColumns = @JoinColumn(name = "emailAmministrativo"),
            inverseJoinColumns = @JoinColumn(name = "idRuolo")
    )
    private List<Ruolo> ruoli;
}
