package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Atleta;

import java.util.List;

public interface AtletaService {
    List<Atleta> findAll();
    Atleta findByEmail(String email);
    boolean checkUser(String email, String password);
    void save(Atleta a);
    void deleteByEmail(String email);
}
