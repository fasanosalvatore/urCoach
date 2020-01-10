package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.control.pacchetti.PacchettiControl;
import it.unisa.di.urcoach.model.entity.Categoria;
import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.repository.PacchettoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacchettoServiceImplTest {

    PacchettoRepository pacchettoRepository;
    PacchettoServiceImpl pacchettoService;

    @BeforeEach
    void init() {
        pacchettoRepository = mock(PacchettoRepository.class);
        pacchettoService = new PacchettoServiceImpl(pacchettoRepository);
    }

    @Test
    void findAll() {
        List<Pacchetto> pacchetti = new ArrayList<>();
        Pacchetto p1 = new Pacchetto();
        Pacchetto p2 = new Pacchetto();
        pacchetti.add(p1);
        pacchetti.add(p2);
        when(pacchettoRepository.findAllByOrderByNomeAsc()).thenReturn(pacchetti);
        List<Pacchetto> found = pacchettoService.findAll();
        assertEquals(found.size(), 2);
        verify(pacchettoRepository).findAllByOrderByNomeAsc();
    }

    @Test
    void findAllByCategoria() {
        List<Pacchetto> pacchetti = new ArrayList<>();
        Pacchetto p1 = new Pacchetto();
        Pacchetto p2 = new Pacchetto();
        Categoria cat = new Categoria();
        cat.setNome("Definizione");
        p1.setCategoria(cat);
        p2.setCategoria(cat);
        pacchetti.add(p1);
        pacchetti.add(p2);
        when(pacchettoRepository.findByCategoria(cat)).thenReturn(pacchetti);
        List<Pacchetto> found = pacchettoService.findAllByCategoria(cat);
        assertEquals(found.size(), 2);
        verify(pacchettoRepository).findByCategoria(cat);
    }

    @Test
    void findAllByCosto() {
        List<Pacchetto> pacchetti = new ArrayList<>();
        Pacchetto p1 = new Pacchetto();
        Pacchetto p2 = new Pacchetto();
        p1.setCosto(300);
        p2.setCosto(200);
        pacchetti.add(p1);
        pacchetti.add(p2);
        when(pacchettoRepository.findAllByCostoIsLessThan(500)).thenReturn(pacchetti);
        List<Pacchetto> found = pacchettoService.findAllByCosto(500);
        assertEquals(found.size(), 2);
        verify(pacchettoRepository).findAllByCostoIsLessThan(500);
    }

    @Test
    void findAllByPersonalTrainer() {
        List<Pacchetto> pacchetti = new ArrayList<>();
        PersonalTrainer pt = new PersonalTrainer();
        pt.setEmail("salvatore@gmail.com");
        Pacchetto p1 = new Pacchetto();
        Pacchetto p2 = new Pacchetto();
        p1.setPersonalTrainer(pt);
        p2.setPersonalTrainer(pt);
        pacchetti.add(p1);
        pacchetti.add(p2);
        when(pacchettoRepository.findByPersonalTrainer(pt)).thenReturn(pacchetti);
        List<Pacchetto> found = pacchettoService.findAllByPersonalTrainer(pt);
        assertEquals(found.size(), 2);
        verify(pacchettoRepository).findByPersonalTrainer(pt);
    }

    @Test
    void findLast() {
        List<Pacchetto> pacchetti = new ArrayList<>();
        Pacchetto p1 = new Pacchetto();
        p1.setDataCreazione(Date.valueOf("2019-12-10"));
        Pacchetto p2 = new Pacchetto();
        p1.setDataCreazione(Date.valueOf("2019-11-10"));
        Pacchetto p3 = new Pacchetto();
        p1.setDataCreazione(Date.valueOf("2019-10-10"));
        Pacchetto p4 = new Pacchetto();
        p1.setDataCreazione(Date.valueOf("2019-9-10"));
        Pacchetto p5 = new Pacchetto();
        p1.setDataCreazione(Date.valueOf("2019-8-10"));
        pacchetti.add(p1);
        pacchetti.add(p2);
        pacchetti.add(p3);
        pacchetti.add(p4);
        when(pacchettoRepository.findTop4ByOrderByDataCreazioneDesc()).thenReturn(pacchetti);
        List<Pacchetto> found = pacchettoService.findLast();
        assertEquals(found.size(), 4);
        verify(pacchettoRepository).findTop4ByOrderByDataCreazioneDesc();
    }

    @Test
    void findById() {
        Pacchetto pacchetto = new Pacchetto();
        pacchetto.setIdPacchetto(1);
        when(pacchettoRepository.findById(1)).thenReturn(java.util.Optional.of(pacchetto));
        Pacchetto found = pacchettoService.findById(1);
        assertEquals(found.getIdPacchetto(), 1);
        verify(pacchettoRepository).findById(1);
    }

    @Test
    void findByNome() {
        List<Pacchetto> pacchetti = new ArrayList<>();
        Pacchetto p1 = new Pacchetto();
        Pacchetto p2 = new Pacchetto();
        p1.setNome("Dimagrire in 100 giorni");
        p2.setNome("Dimagrisci velocemente!");
        pacchetti.add(p1);
        pacchetti.add(p2);
        when(pacchettoRepository.findAllByNomeContaining("Dim")).thenReturn(pacchetti);
        List<Pacchetto> found = pacchettoService.findByNome("Dim");
        assertEquals(found.size(), 2);
        verify(pacchettoRepository).findAllByNomeContaining("Dim");
    }

    @Test
    void save() {
        Pacchetto pacchetto = new Pacchetto();
        pacchettoService.save(pacchetto);
        verify(pacchettoRepository).save(pacchetto);
    }

    @Test
    void deleteById() {
        pacchettoService.deleteById(1);
        verify(pacchettoRepository).deleteById(1);
    }
}