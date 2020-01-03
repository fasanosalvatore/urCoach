package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import it.unisa.di.urcoach.Model.Repository.PersonalTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalTrainerImpl implements PersonalTrainerService {

    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;

    @Override
    public List<PersonalTrainer> findAll() {
        return personalTrainerRepository.findAllByOrderByCognomeAsc();
    }

    @Override
    public PersonalTrainer findByEmail(String email) {
        Optional<PersonalTrainer> result = personalTrainerRepository.findById(email);
        PersonalTrainer e = null;
        if (result.isPresent())
            e = result.get();
        return e;
    }

    @Override
    public List<PersonalTrainer> findByVerificato(int verificato) {
        return personalTrainerRepository.findAllByVerificato(verificato);
    }

    @Override
    public PersonalTrainer checkUser(String email, String password) {
        PersonalTrainer pt = findByEmail(email);
        if(pt == null)
            return null;
        if (pt.getPassword().equals(password))
            return pt;
        return null;
    }

    @Override
    public void save(PersonalTrainer pt) {
        personalTrainerRepository.save(pt);
    }

    @Override
    public void deleteByEmail(String email) {
        personalTrainerRepository.deleteById(email);
    }
}
