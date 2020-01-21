package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.repository.PersonalTrainerRepository;
import org.aspectj.weaver.patterns.PerObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonalTrainerServiceImplTest {

    PersonalTrainerRepository personalTrainerRepository;
    PersonalTrainerService personalTrainerService;

    @BeforeEach
    void init() {
        personalTrainerRepository = mock(PersonalTrainerRepository.class);
        personalTrainerService = new PersonalTrainerServiceImpl(personalTrainerRepository);
    }

    @Test
    void findAll() {
        List<PersonalTrainer> trainers = new ArrayList<>();
        PersonalTrainer t1 = new PersonalTrainer();
        PersonalTrainer t2 = new PersonalTrainer();
        PersonalTrainer t3 = new PersonalTrainer();
        trainers.add(t1);
        trainers.add(t2);
        trainers.add(t3);
        when(personalTrainerRepository.findAllByOrderByCognomeAsc()).thenReturn(trainers);
        List<PersonalTrainer> found = personalTrainerService.findAll();
        assertEquals(found.size(), 3);
        verify(personalTrainerRepository).findAllByOrderByCognomeAsc();
    }

    @Test
    void findByEmail() {
        PersonalTrainer pt = new PersonalTrainer();
        pt.setEmail("salvatore@gmail.com");
        when(personalTrainerRepository.findById("salvatore@gmail.com")).thenReturn(java.util.Optional.of(pt));
        PersonalTrainer found = personalTrainerService.findByEmail("salvatore@gmail.com");
        assertEquals(found.getEmail(), "salvatore@gmail.com");
        verify(personalTrainerRepository).findById("salvatore@gmail.com");
    }

    @Test
    void findByVerificato() {
        List<PersonalTrainer> trainers = new ArrayList<>();
        PersonalTrainer t1 = new PersonalTrainer();
        PersonalTrainer t2 = new PersonalTrainer();
        PersonalTrainer t3 = new PersonalTrainer();
        t1.setVerificato(1);
        t2.setVerificato(1);
        t3.setVerificato(1);
        trainers.add(t1);
        trainers.add(t2);
        trainers.add(t3);
        when(personalTrainerRepository.findAllByVerificato(1)).thenReturn(trainers);
        List<PersonalTrainer> found = personalTrainerService.findByVerificato(1);
        assertEquals(found.size(), 3);
        verify(personalTrainerRepository).findAllByVerificato(1);
    }

    @Test
    void checkUser() {
        PersonalTrainer pt = new PersonalTrainer();
        pt.setEmail("salvatore@gmail.com");
        pt.setPassword("Ciao1");
        when(personalTrainerRepository.findById("salvatore@gmail.com")).thenReturn(java.util.Optional.of(pt));
        PersonalTrainer found = personalTrainerService.checkUser("salvatore@gmail.com", "Ciao1");
        assertEquals(found.getEmail(), "salvatore@gmail.com");
        assertEquals(found.getPassword(), "Ciao1");
        verify(personalTrainerRepository).findById("salvatore@gmail.com");
    }


    @Test
    void save() {
        PersonalTrainer pt = new PersonalTrainer();
        personalTrainerService.save(pt);
        verify(personalTrainerRepository).save(pt);
    }

    @Test
    void deleteByEmail() {
        personalTrainerService.deleteByEmail("pasquale@gmail.com");
        verify(personalTrainerRepository).deleteById("pasquale@gmail.com");
    }
}