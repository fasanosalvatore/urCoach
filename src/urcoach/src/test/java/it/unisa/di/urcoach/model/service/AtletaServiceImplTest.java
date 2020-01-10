package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Acquisto;
import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.repository.AtletaRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtletaServiceImplTest {

    AtletaRepository atletaRepository;
    AtletaService atletaService;

    @BeforeEach
    void init() {
        atletaRepository = mock(AtletaRepository.class);
        atletaService = new AtletaServiceImpl(atletaRepository);
    }

    @Test
    void findAll() {
        List<Atleta> atleti = new ArrayList<>();
        Atleta atleta1 = new Atleta();
        Atleta atleta2 = new Atleta();
        Atleta atleta3 = new Atleta();
        atleti.add(atleta1);
        atleti.add(atleta2);
        atleti.add(atleta3);
        when(atletaRepository.findAll()).thenReturn(atleti);
        List<Atleta> found = atletaService.findAll();
        assertEquals(found.size(), 3);
        verify(atletaRepository, times(1)).findAll();
    }

    @Test
    void findByEmail() {
        when(atletaRepository.findById("salvatore@gmail.com")).thenReturn(java.util.Optional.of(new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1")));
        Atleta atleta = atletaService.findByEmail("salvatore@gmail.com");
        assertEquals(atleta.getEmail(), "salvatore@gmail.com");
        verify(atletaRepository, times(1)).findById("salvatore@gmail.com");
    }

    @Test
    void checkUser() {
        when(atletaRepository.findById("salvatore@gmail.com")).thenReturn(java.util.Optional.of(new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1")));
        Atleta atleta = atletaService.checkUser("salvatore@gmail.com", "Ciao1");
        assertEquals(atleta.getEmail(), "salvatore@gmail.com");
        assertEquals(atleta.getPassword(), "Ciao1");
        verify(atletaRepository, times(1)).findById("salvatore@gmail.com");
    }

    @Test
    void save() {
        Atleta atleta = new Atleta();
        atletaService.save(atleta);
        verify(atletaRepository, times(1)).save(atleta);
    }

    @Test
    void deleteByEmail() {
        atletaService.deleteByEmail("pasquale@gmail.com");
        verify(atletaRepository, times(1)).deleteById("pasquale@gmail.com");
    }
}