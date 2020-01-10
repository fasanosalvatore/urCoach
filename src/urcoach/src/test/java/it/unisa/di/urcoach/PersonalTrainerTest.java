package it.unisa.di.urcoach;

import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.repository.PersonalTrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonalTrainerTest {

    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;

    @Test
    public void salvataggioPTCorretto(){
        @Valid
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        try {
            personalTrainerRepository.save(pt);
        } catch (TransactionSystemException e) {fail("Non deve restituire una ConstraintViolation Exception");}
    }

    @Test
    public void salvataggioPTEmail(){
        @Valid
        PersonalTrainer pt = new PersonalTrainer("salvatoregmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        try {
            personalTrainerRepository.save(pt);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioPTCodiceFiscale(){
        @Valid
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT9889H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        try {
            personalTrainerRepository.save(pt);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioPTNome(){
        @Valid
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "1", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        try {
            personalTrainerRepository.save(pt);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioPTCognome(){
        @Valid
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "1", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        try {
            personalTrainerRepository.save(pt);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioPTPassword(){
        @Valid
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao", "12345678901", "", 0);
        try {
            personalTrainerRepository.save(pt);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }

    @Test
    public void salvataggioPTPiva(){
        @Valid
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901234", "", 0);
        try {
            personalTrainerRepository.save(pt);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }


}
