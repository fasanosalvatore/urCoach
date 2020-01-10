package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Acquisto;
import it.unisa.di.urcoach.model.entity.Fattura;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;

import java.util.List;

public interface AcquistoService {
    List<Acquisto> findAll();
    List<Acquisto> findByFattura(Fattura f);
    List<Acquisto> findByPersonalTrainer(PersonalTrainer pt);
    void save(Acquisto a);
}
