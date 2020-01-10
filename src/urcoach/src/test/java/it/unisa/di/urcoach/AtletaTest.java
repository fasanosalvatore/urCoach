package it.unisa.di.urcoach;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Repository.AtletaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AtletaTest {

    @Autowired
    private AtletaRepository atletaRepository;

    @Test
    public void salvataggioAtletaCorretto() throws Exception{
        @Valid
        Atleta atleta = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        try {
            atletaRepository.save(atleta);
        } catch (TransactionSystemException e) {fail("Non deve restituire una ConstraintViolation Exception");}
    }

    @Test
    public void salvataggioAtletaEmail() throws Exception{
        @Valid
        Atleta atleta = new Atleta("salvatoregmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        try {
            atletaRepository.save(atleta);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioAtletaNome() throws Exception{
        @Valid
        Atleta atleta = new Atleta("salvatore@gmail.com", "1", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        try {
            atletaRepository.save(atleta);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioAtletaCognome() throws Exception{
        @Valid
        Atleta atleta = new Atleta("salvatore@gmail.com", "Salvatore", "1", "FSNSVT902A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        try {
            atletaRepository.save(atleta);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioAtletaCodiceFiscale() throws Exception{
        @Valid
        Atleta atleta = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H8902A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        try {
            atletaRepository.save(atleta);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioAtletaindirizzo() throws Exception{
        @Valid
        Atleta atleta = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli,14", "Ciao1");
        try {
            atletaRepository.save(atleta);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioAtletaPassword() throws Exception{
        @Valid
        Atleta atleta = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao");
        try {
            atletaRepository.save(atleta);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

}
