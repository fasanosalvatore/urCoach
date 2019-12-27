package it.unisa.di.urcoach.Model.Repository;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, String> {

}
