package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.Fattura;
import it.unisa.di.urcoach.model.repository.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FatturaServiceImpl implements FatturaService {

    private final FatturaRepository fatturaRepository;

    public FatturaServiceImpl(FatturaRepository fatturaRepository) {
        this.fatturaRepository = fatturaRepository;
    }

    @Override
    public List<Fattura> findAll() {
        return fatturaRepository.findAll();
    }

    @Override
    public List<Fattura> findByAtleta(Atleta a) {
        return fatturaRepository.findByAtletaOrderByDataDesc(a);
    }

    @Override
    public void save(Fattura f) {
        fatturaRepository.save(f);
    }
}
