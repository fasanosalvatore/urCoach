package it.unisa.di.urcoach.model.repository;

import it.unisa.di.urcoach.model.entity.Categoria;
import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
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
    Pacchetto getOne(int id);
}
