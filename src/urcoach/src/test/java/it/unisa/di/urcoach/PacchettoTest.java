package it.unisa.di.urcoach;

import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.repository.PacchettoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
public class PacchettoTest {

    @Autowired
    private PacchettoRepository pacchettoRepository;

    @Test
    public void salvataggioPacchettoCorretto() {
        @Valid
        Pacchetto p = new Pacchetto("Pacchetto", 800, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        try {
            pacchettoRepository.save(p);
        } catch (ConstraintViolationException e) {fail("Non deve restituire una ConstraintViolation Exception");}
    }

    @Test
    public void salvataggioPacchettoNome() {
        @Valid
        Pacchetto p = new Pacchetto("1", 800, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        try {
            pacchettoRepository.save(p);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (ConstraintViolationException e) {}
    }

    @Test
    public void salvataggioPacchettoCosto() {
        @Valid
        Pacchetto p = new Pacchetto("Pacchetto", 200000, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        try {
            pacchettoRepository.save(p);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (ConstraintViolationException e) {}
    }

    @Test
    public void salvataggioPacchettoDurata() {
        @Valid
        Pacchetto p = new Pacchetto("Pacchetto", 200, 80, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        try {
            pacchettoRepository.save(p);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (ConstraintViolationException e) {}
    }

    @Test
    public void salvataggioPacchettoDescrizione() {
        @Valid
        Pacchetto p = new Pacchetto("Pacchetto", 200, 80, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena223456");
        try {
            pacchettoRepository.save(p);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (ConstraintViolationException e) {}
    }
}
