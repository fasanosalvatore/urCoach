package it.unisa.di.urcoach.Model.Repository;

import it.unisa.di.urcoach.Model.Entity.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Integer> {
}
