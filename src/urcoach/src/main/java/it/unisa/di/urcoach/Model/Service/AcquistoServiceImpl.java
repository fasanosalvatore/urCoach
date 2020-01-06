package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Acquisto;
import it.unisa.di.urcoach.Model.Entity.Fattura;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import it.unisa.di.urcoach.Model.Repository.AcquistoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcquistoServiceImpl implements AcquistoService {

    @Autowired
    private AcquistoRepository acquistoRepository;

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
