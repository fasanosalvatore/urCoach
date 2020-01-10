package it.unisa.di.urcoach;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.Fattura;
import it.unisa.di.urcoach.model.repository.AtletaRepository;
import it.unisa.di.urcoach.model.service.FatturaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.*;

@SpringBootTest
class UrcoachApplicationTests {

    @Autowired
    private FatturaService fatturaService;

    @Autowired
    private AtletaRepository atletaRepository;

    @Test
    public void checkArrays() {
        Atleta atleta = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        atletaRepository.save(atleta);
        Fattura fattura1 = new Fattura(100, atleta, Date.valueOf("2019-12-10"));
        Fattura fattura2 = new Fattura(100, atleta, Date.valueOf("2019-08-10"));
        Fattura fattura3 = new Fattura(100, atleta, Date.valueOf("2019-05-10"));
        /*
        Pacchetto p = new Pacchetto("Pacchetto", 800, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        Acquisto acquisto1 = new Acquisto(fattura1, p, 100);
        Acquisto acquisto2 = new Acquisto(fattura2, p, 100);
        Acquisto acquisto3 = new Acquisto(fattura3, p, 100);
        acquistoService.save(acquisto1);
        acquistoService.save(acquisto2);
        acquistoService.save(acquisto3);
         */
        fatturaService.save(fattura1);
        fatturaService.save(fattura2);
        fatturaService.save(fattura3);
        List<Fattura> expected = Arrays.asList(fattura1, fattura2, fattura3);
        List<Fattura> found = fatturaService.findByAtleta(atleta);
        Assertions.assertThat(expected).isEqualTo(found);
    }

}
