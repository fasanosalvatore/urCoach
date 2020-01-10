package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.*;
import it.unisa.di.urcoach.model.repository.AcquistoRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class AcquistoServiceImplTest {

    AcquistoRepository acquistoRepository;
    AcquistoService acquistoService;

    @Test
    void findAll() {
        acquistoRepository = mock(AcquistoRepository.class);
        acquistoService = new AcquistoServiceImpl(acquistoRepository);
        Acquisto acquisto1 = new Acquisto();
        Acquisto acquisto2 = new Acquisto();
        Acquisto acquisto3 = new Acquisto();
        List<Acquisto> acquisti = new ArrayList<>();
        acquisti.add(acquisto1);
        acquisti.add(acquisto2);
        acquisti.add(acquisto3);
        when(acquistoRepository.findAll()).thenReturn(acquisti);
        List<Acquisto> found = acquistoService.findAll();
        assertEquals(found.size(), 3);
        verify(acquistoRepository, times(1)).findAll();
    }

    @Test
    void findByFattura() {
        acquistoRepository = mock(AcquistoRepository.class);
        acquistoService = new AcquistoServiceImpl(acquistoRepository);
        Atleta atleta = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        Fattura fattura1 = new Fattura(100, atleta, Date.valueOf("2019-12-10"));
        Fattura fattura2 = new Fattura(200, atleta, Date.valueOf("2019-10-10"));
        Pacchetto p = new Pacchetto("Pacchetto", 800, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        Acquisto acquisto1 = new Acquisto(fattura1, p, 100);
        Acquisto acquisto2 = new Acquisto(fattura1, p, 100);
        Acquisto acquisto3 = new Acquisto(fattura1, p, 100);
        Acquisto acquisto4 = new Acquisto(fattura2, p, 100);
        List<Acquisto> acquisti = new ArrayList<>();
        acquisti.add(acquisto1);
        acquisti.add(acquisto2);
        acquisti.add(acquisto3);
        when(acquistoRepository.findByFattura(fattura1)).thenReturn(acquisti);
        List<Acquisto> found = acquistoService.findByFattura(fattura1);
        assertEquals(found.size(), 3);
        verify(acquistoRepository, times(1)).findByFattura(fattura1);
    }

    @Test
    void findByPersonalTrainer() {
        acquistoRepository = mock(AcquistoRepository.class);
        acquistoService = new AcquistoServiceImpl(acquistoRepository);
        Atleta atleta = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        Fattura fattura1 = new Fattura(100, atleta, Date.valueOf("2019-12-10"));
        Fattura fattura2 = new Fattura(200, atleta, Date.valueOf("2019-10-10"));
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 1);
        Pacchetto p = new Pacchetto("Pacchetto", 800, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        p.setPersonalTrainer(pt);
        Acquisto acquisto1 = new Acquisto(fattura1, p, 100);
        Acquisto acquisto2 = new Acquisto(fattura1, p, 100);
        Acquisto acquisto3 = new Acquisto(fattura1, p, 100);
        List<Acquisto> acquisti = new ArrayList<>();
        acquisti.add(acquisto1);
        acquisti.add(acquisto2);
        acquisti.add(acquisto3);
        when(acquistoRepository.findByPacchetto_PersonalTrainer(pt)).thenReturn(acquisti);
        List<Acquisto> found = acquistoService.findByPersonalTrainer(pt);
        assertEquals(found.size(), 3);
        verify(acquistoRepository, times(1)).findByPacchetto_PersonalTrainer(pt);
    }

    @Test
    void save() {
        acquistoRepository = mock(AcquistoRepository.class);
        acquistoService = new AcquistoServiceImpl(acquistoRepository);
        Acquisto acquisto = new Acquisto();
        acquistoService.save(acquisto);
        verify(acquistoRepository, times(1)).save(acquisto);
    }
}