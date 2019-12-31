package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Acquisto;

import java.util.List;

public interface AcquistoService {
    List<Acquisto> findAll();
    void save(Acquisto a);
}
