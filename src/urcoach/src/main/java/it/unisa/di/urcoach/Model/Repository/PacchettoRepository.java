package it.unisa.di.urcoach.Model.Repository;

import it.unisa.di.urcoach.Model.Entity.Categoria;
import it.unisa.di.urcoach.Model.Entity.Pacchetto;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacchettoRepository extends JpaRepository<Pacchetto, Integer> {
    List<Pacchetto> findAllByOrderByNomeAsc();
    List<Pacchetto> findTop4ByOrderByDataCreazioneDesc();
    List<Pacchetto> findAllByNomeContaining(String nome);
    List<Pacchetto> findAllByCostoIsLessThan(float valore);
    //List<Pacchetto> findAllByCostoBetween(float limiteInf, float limiteSup);
    List<Pacchetto> findByCategoria(Categoria categoria);
    List<Pacchetto> findByPersonalTrainer(PersonalTrainer personalTrainer);
}
