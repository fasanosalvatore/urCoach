package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.PersonalTrainer;

import java.util.List;

public interface PersonalTrainerService{
    List<PersonalTrainer> findAll();
    PersonalTrainer findByEmail(String email);
    List<PersonalTrainer> findByVerificato(int verificato);
    PersonalTrainer checkUser(String email, String password);
    void save(PersonalTrainer pt);
    void deleteByEmail(String email);
}
