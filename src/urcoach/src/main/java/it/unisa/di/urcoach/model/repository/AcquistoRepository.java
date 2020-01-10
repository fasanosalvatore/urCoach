package it.unisa.di.urcoach.model.repository;

import it.unisa.di.urcoach.model.entity.Acquisto;
import it.unisa.di.urcoach.model.entity.AcquistoID;
import it.unisa.di.urcoach.model.entity.Fattura;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto, AcquistoID> {
    List<Acquisto> findByFattura(Fattura f);
    List<Acquisto> findByPacchetto_PersonalTrainer(PersonalTrainer pt);
}
