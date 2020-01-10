package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.repository.PersonalTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalTrainerServiceImpl implements PersonalTrainerService {

    private final PersonalTrainerRepository personalTrainerRepository;

    public PersonalTrainerServiceImpl(PersonalTrainerRepository personalTrainerRepository) {
        this.personalTrainerRepository = personalTrainerRepository;
    }

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
