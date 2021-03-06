package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Categoria;
import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.repository.PacchettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacchettoServiceImpl implements PacchettoService{

    private final PacchettoRepository pacchettoRepository;

    public PacchettoServiceImpl(PacchettoRepository pacchettoRepository) {
        this.pacchettoRepository = pacchettoRepository;
    }

    @Override
    public List<Pacchetto> findAll() {
        return pacchettoRepository.findAllByOrderByNomeAsc();
    }

    @Override
    public List<Pacchetto> findAllByCategoria(Categoria categoria) {
        return pacchettoRepository.findByCategoria(categoria);
    }

    @Override
    public List<Pacchetto> findAllByCosto(float valore) {
        return pacchettoRepository.findAllByCostoIsLessThan(valore);
    }

    @Override
    public List<Pacchetto> findAllByPersonalTrainer(PersonalTrainer pt) {
        return pacchettoRepository.findByPersonalTrainer(pt);
    }

    @Override
    public List<Pacchetto> findLast() {
        return pacchettoRepository.findTop4ByOrderByDataCreazioneDesc();
    }

    @Override
    public Pacchetto findById(int id) {
        Optional<Pacchetto> result = pacchettoRepository.findById(id);
        Pacchetto e = null;
        if (result.isPresent())
            e = result.get();
        return e;
    }

    @Override
    public List<Pacchetto> findByNome(String nome) {
        return pacchettoRepository.findAllByNomeContaining(nome);
    }

    @Override
    public void save(Pacchetto p) {
        pacchettoRepository.save(p);
    }

    @Override
    public void deleteById(int id) {
        pacchettoRepository.deleteById(id);
    }
}
