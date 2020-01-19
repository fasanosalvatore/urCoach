package it.unisa.di.urcoach.control.pacchetti;

import it.unisa.di.urcoach.model.entity.Categoria;
import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.repository.PacchettoRepository;
import it.unisa.di.urcoach.model.repository.PersonalTrainerRepository;
import it.unisa.di.urcoach.model.service.CategoriaService;
import it.unisa.di.urcoach.model.service.PacchettoService;
import it.unisa.di.urcoach.model.service.PersonalTrainerService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PacchettiControlIT {

    @Autowired
    PacchettoService pacchettoService;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    PersonalTrainerService personalTrainerService;
    @Autowired
    PersonalTrainerRepository personalTrainerRepository;
    @Autowired
    PacchettoRepository pacchettoRepository;

    @Autowired
    private MockMvc mockMvc;


    @BeforeClass
    @BeforeEach
    void setup() {
        personalTrainerRepository.deleteAll();
        pacchettoRepository.deleteAll();
    }

    @Test
    void filtriPacchetti() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in tot giorni");
        Categoria c = new Categoria();
        c.setNome("Definizione");
        p1.setCategoria(c);
        p1.setCosto(500);
        p1.setDurata(2);
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        p1.setPersonalTrainer(a);
        personalTrainerService.save(a);
        pacchettoService.save(p1);
        mockMvc.perform(get("/api/pacchettiFiltri")
                .param("nomeTrainer", "salviofasano@gmail.com")
                .param("categoria", "Definizione")
                .param("prezzo", "800"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void showPacchetti() throws Exception{
        mockMvc.perform(get("/pacchetti"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/pacchetti"))
                .andExpect(model().attributeExists("categorie", "trainers", "trainer", "atleta"));
    }

    /*

    @Test
    void showPacchetto() throws Exception{
        Categoria c = new Categoria();
        c.setNome("Definizione");
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        a.setCognome("Fasano");
        a.setNome("Salvatore");
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(6);
        p1.setNome("Dimagrire in tot giorni");
        p1.setPersonalTrainer(a);
        p1.setCategoria(c);
        p1.setDurata(3);
        p1.setCosto(300);
        personalTrainerService.save(a);
        pacchettoService.save(p1);
        mockMvc.perform(get("/pacchetti/6"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/pacchetto"))
                .andExpect(model().attributeExists("p", "trainer", "atleta"))
                .andExpect(model().attribute("p", p1));
    }*/

    @Test
    void gestionePacchetti_NoTrainer() throws Exception{
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("logged", true))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void gestionePacchetti_NoAzione() throws Exception{
        PersonalTrainer trainer = new PersonalTrainer();
        trainer.setEmail("salviofasano@gmail.com");
        personalTrainerService.save(trainer);
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("trainer", trainer)
                .sessionAttr("logged", true))
                .andExpect(status().isOk())
                .andExpect(view().name("View/gestionePacchetti"))
                .andExpect(model().attributeExists("pacchetti"));
    }

    @Test
    void gestionePacchetti_AzioneNuovo() throws Exception{
        PersonalTrainer trainer = new PersonalTrainer();
        trainer.setEmail("salviofasano@gmail.com");
        personalTrainerService.save(trainer);
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("trainer", trainer)
                .sessionAttr("logged", true)
                .param("azione", "nuovo"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/nuovoPacchetto"))
                .andExpect(model().attributeExists("pacchetto", "categorie"));
    }

    /*

    @Test
    void gestionePacchetti_AzioneEdit() throws Exception{
        Categoria c = new Categoria();
        c.setNome("Definizione");
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        a.setCognome("Fasano");
        a.setNome("Salvatore");
        personalTrainerService.save(a);
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(6);
        p1.setNome("Dimagrire in tot giorni");
        p1.setPersonalTrainer(a);
        p1.setCategoria(c);
        p1.setDurata(2);
        pacchettoService.save(p1);
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("trainer", a)
                .sessionAttr("logged", true)
                .param("azione", "edit")
                .param("id", "6"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/nuovoPacchetto"))
                .andExpect(model().attributeExists("edit", "pacchetto", "categorie"));
    }

*/


    @Test
    void gestionePacchetti_AzioneRimuovi() throws Exception{
        Categoria c = new Categoria();
        c.setNome("Definizione");
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(6);
        p1.setNome("Dimagrire in tot giorni");
        p1.setPersonalTrainer(a);
        p1.setCategoria(c);
        p1.setCosto(300);
        p1.setDurata(2);
        personalTrainerService.save(a);
        pacchettoService.save(p1);
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("trainer", a)
                .sessionAttr("logged", true)
                .param("azione", "rimuovi")
                .param("id", "6"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/areaPersonale/gestionePacchetti"));
    }

    @Test
    void salvaPacchetto_Valid() throws Exception{
        Categoria c = new Categoria();
        c.setNome("Definizione");
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        Pacchetto p = new Pacchetto("Pacchetto", 800, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        p.setPersonalTrainer(a);
        personalTrainerService.save(a);
        mockMvc.perform(post("/areaPersonale/gestionePacchetti/salva")
                .param("categoria", "Massa")
                .sessionAttr("trainer", a)
                .sessionAttr("logged", true)
                .flashAttr("pacchetto", p))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/areaPersonale/gestionePacchetti"));
    }

    @Test
    void salvaPacchetto_NonValid() throws Exception{
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        Pacchetto p = new Pacchetto("1", 2000, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        p.setPersonalTrainer(a);
        mockMvc.perform(post("/areaPersonale/gestionePacchetti/salva")
                .sessionAttr("trainer", a)
                .sessionAttr("logged", true)
                .flashAttr("pacchetto", p))
                .andExpect(status().isOk())
                .andExpect(view().name("View/nuovoPacchetto"))
                .andExpect(model().attributeExists("pacchetto", "categorie"));
    }

    @Test
    void salvaPacchetto_NoPt() throws Exception{
        Pacchetto p = new Pacchetto("1", 2000, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        mockMvc.perform(post("/areaPersonale/gestionePacchetti/salva")
                .sessionAttr("logged", true)
                .flashAttr("pacchetto", p))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }
}