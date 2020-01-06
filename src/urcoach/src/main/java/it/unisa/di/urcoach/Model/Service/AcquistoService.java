package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Acquisto;
import it.unisa.di.urcoach.Model.Entity.Fattura;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;

import java.util.List;

public interface AcquistoService {
    List<Acquisto> findAll();
    List<Acquisto> findByFattura(Fattura f);
    List<Acquisto> findByPersonalTrainer(PersonalTrainer pt);
    void save(Acquisto a);
}
