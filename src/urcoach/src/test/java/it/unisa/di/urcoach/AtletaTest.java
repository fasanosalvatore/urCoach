package it.unisa.di.urcoach;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.repository.AtletaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AtletaTest {

    @Autowired
    private AtletaRepository atletaRepository;



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
