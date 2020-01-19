package it.unisa.di.urcoach.control.carrello;

import it.unisa.di.urcoach.model.entity.*;
import it.unisa.di.urcoach.model.service.AcquistoService;
import it.unisa.di.urcoach.model.service.AtletaService;
import it.unisa.di.urcoach.model.service.PacchettoService;
import it.unisa.di.urcoach.model.service.PersonalTrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CarrelloControlIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacchettoService pacchettoService;

    @Autowired
    private AtletaService atletaService;

    @Autowired
    private PersonalTrainerService personalTrainerService;

    @Autowired
    private AcquistoService acquistoService;

    @Test
    void aggiungiCarrello() throws Exception{
        MockHttpServletRequest request = mockMvc.perform(post("/carrello")
                .param("idPacchetto", "1")
                .param("azione", "aggiungi"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"))
                .andReturn().getRequest();
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        Pacchetto p1 = pacchettoService.findById(1);
        assertTrue(carrello.getPacchetti().contains(p1));
    }

    @Test
    void mostraCarrello() throws Exception{
        mockMvc.perform(get("/carrello")
                .sessionAttr("carrello", new Carrello()))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"));
    }

    @Test
    void checkout() throws Exception{
        Atleta a = new Atleta();
        a.setEmail("salviofasano@gmail.com");
        atletaService.save(a);
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(7);
        p1.setNome("Dimagrire in tot giorni");
        p1.setDurata(2);
        pacchettoService.save(p1);
        Carrello carrello = new Carrello();
        carrello.put(p1);
        mockMvc.perform(get("/checkout")
                .sessionAttr("atleta", a)
                .sessionAttr("carrello", carrello))
                .andExpect(status().isOk())
                .andExpect(view().name("View/checkout"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"));
    }

    @Test
    void nuovaFattura() throws Exception{
        Pacchetto p1 = pacchettoService.findById(1);
        Pacchetto p2 = pacchettoService.findById(2);
        Carrello carrello = new Carrello();
        carrello.put(p1);
        carrello.put(p2);
        mockMvc.perform(post("/nuovaFattura")
                .param("costo", "200")
                .param("atleta", "salviofasano@gmail.com")
                .param("cc", "5333171030866194")
                .param("dataScadenza", "01/43")
                .param("cvc", "123")
                .sessionAttr("carrello", carrello))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/areaPersonale"))
                .andExpect(flash().attributeCount(0));
    }
}