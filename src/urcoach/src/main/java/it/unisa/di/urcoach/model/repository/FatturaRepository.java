package it.unisa.di.urcoach.model.repository;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Integer> {
    List<Fattura> findByAtletaOrderByDataDesc(Atleta a);
}
