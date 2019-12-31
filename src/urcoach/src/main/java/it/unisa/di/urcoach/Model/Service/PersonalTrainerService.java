package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;

import java.util.List;

public interface PersonalTrainerService{
    List<PersonalTrainer> findAll();
    PersonalTrainer findByEmail(String email);
    List<PersonalTrainer> findByVerificato(boolean verificato);
    boolean checkUser(String email, String password);
    void save(PersonalTrainer pt);
    void deleteByEmail(String email);
}
