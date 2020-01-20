package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.Fattura;

import java.util.List;

public interface FatturaService {
    List<Fattura> findAll();
    List<Fattura> findByAtleta(Atleta a);
    void save(Fattura f);
}
