package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.repository.AtletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtletaServiceImpl implements AtletaService{

    private final AtletaRepository atletaRepository;

    public AtletaServiceImpl(AtletaRepository atletaRepository) {
        this.atletaRepository = atletaRepository;
    }

    @Override
    public List<Atleta> findAll() {
        return atletaRepository.findAll();
    }

    @Override
    public Atleta findByEmail(String email) {
        Optional<Atleta> result = atletaRepository.findById(email);
        Atleta e = null;
        if (result.isPresent())
            e = result.get();
        return e;
    }

    @Override
    public Atleta checkUser(String email, String password) {
        Atleta atleta = findByEmail(email);
        if (atleta == null)
            return null;
        if (atleta.getPassword().equals(password))
            return atleta;
        return null;
    }

    @Override
    public void save(Atleta a) {
        atletaRepository.save(a);
    }

    @Override
    public void deleteByEmail(String email) {
        atletaRepository.deleteById(email);
    }
}
