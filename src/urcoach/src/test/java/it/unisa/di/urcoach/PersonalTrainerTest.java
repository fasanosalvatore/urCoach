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
    public void salvataggioPTPiva(){
        @Valid
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901234", "", 0);
        try {
            personalTrainerRepository.save(pt);
            fail("Deve restituire una ConstraintViolation Exception");
        } catch (TransactionSystemException e) {}
    }


}
