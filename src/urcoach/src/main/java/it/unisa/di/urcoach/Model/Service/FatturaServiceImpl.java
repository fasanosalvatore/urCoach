package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Entity.Fattura;
import it.unisa.di.urcoach.Model.Repository.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FatturaServiceImpl implements FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Override
    public List<Fattura> findAll() {
        return fatturaRepository.findAll();
    }

    @Override
    public List<Fattura> findByAtleta(Atleta a) {
        return fatturaRepository.findByAtletaOrderByDataDesc(a);
    }

    @Override
    public void save(Fattura a) {
        fatturaRepository.save(a);
    }
}
