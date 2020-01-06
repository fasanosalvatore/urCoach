package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Entity.Fattura;

import java.util.List;

public interface FatturaService {
    List<Fattura> findAll();
    List<Fattura> findByAtleta(Atleta a);
    void save(Fattura a);
}
