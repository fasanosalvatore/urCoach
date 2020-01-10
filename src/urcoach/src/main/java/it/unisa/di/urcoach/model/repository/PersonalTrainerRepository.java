package it.unisa.di.urcoach.model.repository;

import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, String> {
    List<PersonalTrainer> findAllByOrderByCognomeAsc();
    List<PersonalTrainer> findAllByVerificato(int verificato);
}
