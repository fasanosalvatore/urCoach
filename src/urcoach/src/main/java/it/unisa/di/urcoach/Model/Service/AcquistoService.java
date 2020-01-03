package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Acquisto;
import it.unisa.di.urcoach.Model.Entity.Fattura;

import java.util.List;

public interface AcquistoService {
    List<Acquisto> findAll();
    List<Acquisto> findByFattura(Fattura f);
    void save(Acquisto a);
}
