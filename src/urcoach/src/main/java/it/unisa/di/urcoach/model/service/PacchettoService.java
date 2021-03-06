package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Categoria;
import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;

import java.util.List;

public interface PacchettoService {
    List<Pacchetto> findAll();
    List<Pacchetto> findAllByCategoria(Categoria categoria);
    List<Pacchetto> findAllByCosto(float valore);
    List<Pacchetto> findAllByPersonalTrainer(PersonalTrainer pt);
    List<Pacchetto> findLast();
    List<Pacchetto> findByNome(String nome);
    Pacchetto findById(int id);
    void save(Pacchetto p);
    void deleteById(int id);
}
