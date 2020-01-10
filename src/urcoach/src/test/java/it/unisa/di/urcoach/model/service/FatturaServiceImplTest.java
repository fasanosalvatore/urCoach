package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.Fattura;
import it.unisa.di.urcoach.model.repository.FatturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FatturaServiceImplTest {

    FatturaRepository fatturaRepository;
    FatturaService fatturaService;

    @BeforeEach
    void init() {
        fatturaRepository = mock(FatturaRepository.class);
        fatturaService = new FatturaServiceImpl(fatturaRepository);
    }

    @Test
    void findAll() {
        List<Fattura> fatture = new ArrayList<>();
        Fattura f1 = new Fattura();
        Fattura f2 = new Fattura();
        fatture.add(f1);
        fatture.add(f2);
        when(fatturaRepository.findAll()).thenReturn(fatture);
        List<Fattura> found = fatturaService.findAll();
        assertEquals(found.size(), 2);
        verify(fatturaRepository).findAll();
    }

    @Test
    void findByAtleta() {
        Atleta a = new Atleta();
        a.setEmail("salvatore@gmail.com");
        List<Fattura> fatture = new ArrayList<>();
        Fattura f1 = new Fattura(100, a);
        Fattura f2 = new Fattura(100, a);
        fatture.add(f1);
        fatture.add(f2);
        when(fatturaRepository.findByAtletaOrderByDataDesc(a)).thenReturn(fatture);
        List<Fattura> found = fatturaService.findByAtleta(a);
        assertEquals(found.size(), 2);
        for (Fattura f : found) {
            assertEquals(f.getAtleta(), a);
        }
        verify(fatturaRepository).findByAtletaOrderByDataDesc(a);
    }

    @Test
    void save() {
        Fattura f = new Fattura();
        fatturaService.save(f);
        verify(fatturaRepository).save(f);
    }
}