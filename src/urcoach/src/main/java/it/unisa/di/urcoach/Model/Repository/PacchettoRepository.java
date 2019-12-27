package it.unisa.di.urcoach.Model.Repository;

import it.unisa.di.urcoach.Model.Entity.Pacchetto;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacchettoRepository extends JpaRepository<Pacchetto, Integer> {
    List<Pacchetto> findByCategoria(String categoria);
    List<Pacchetto> findByPersonalTrainer(PersonalTrainer personalTrainer);
}
