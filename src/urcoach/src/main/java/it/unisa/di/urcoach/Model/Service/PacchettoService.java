package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Categoria;
import it.unisa.di.urcoach.Model.Entity.Pacchetto;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;

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
    void saveOrUpdate(int id);
    void deleteById(int id);
}
