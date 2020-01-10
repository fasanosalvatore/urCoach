package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Acquisto;
import it.unisa.di.urcoach.model.entity.Fattura;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.repository.AcquistoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcquistoServiceImpl implements AcquistoService {

    private final AcquistoRepository acquistoRepository;

    public AcquistoServiceImpl(AcquistoRepository acquistoRepository) {
        this.acquistoRepository = acquistoRepository;
    }

    @Override
    public List<Acquisto> findAll() {
        return acquistoRepository.findAll();
    }

    @Override
    public List<Acquisto> findByFattura(Fattura f) {
        return acquistoRepository.findByFattura(f);
    }

    @Override
    public List<Acquisto> findByPersonalTrainer(PersonalTrainer pt) {
        return acquistoRepository.findByPacchetto_PersonalTrainer(pt);
    }

    @Override
    public void save(Acquisto a) {
        acquistoRepository.save(a);
    }
}
