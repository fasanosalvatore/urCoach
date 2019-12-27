package it.unisa.di.urcoach.Model.Repository;

import it.unisa.di.urcoach.Model.Entity.Pacchetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacchettoRepository extends JpaRepository<Pacchetto, Integer> {
}
