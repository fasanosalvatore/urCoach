package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Repository.AtletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtletaServiceImpl implements AtletaService{

    @Autowired
    private AtletaRepository atletaRepository;

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
    public boolean checkUser(String email, String password) {
        Atleta atleta = findByEmail(email);
        if (atleta == null)
            return false;
        return atleta.getPassword().equals(password);
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
