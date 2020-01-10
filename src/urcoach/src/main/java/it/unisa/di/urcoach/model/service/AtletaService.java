package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Atleta;

import java.util.List;

public interface AtletaService {
    List<Atleta> findAll();
    Atleta findByEmail(String email);
    Atleta checkUser(String email, String password);
    void save(Atleta a);
    void deleteByEmail(String email);
}
