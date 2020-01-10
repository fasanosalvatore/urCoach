package it.unisa.di.urcoach.model.repository;

import it.unisa.di.urcoach.model.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, String> {

}
