package it.unisa.di.urcoach.Model.Repository;

import it.unisa.di.urcoach.Model.Entity.Acquisto;
import it.unisa.di.urcoach.Model.Entity.AcquistoID;
import it.unisa.di.urcoach.Model.Entity.Fattura;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto, AcquistoID> {
    List<Acquisto> findByFattura(Fattura f);
    List<Acquisto> findByPacchetto_PersonalTrainer(PersonalTrainer pt);
}
