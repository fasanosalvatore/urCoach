package it.unisa.di.urcoach.control.pacchetti;

import it.unisa.di.urcoach.control.carrello.CarrelloControl;
import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.Categoria;
import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.service.CategoriaService;
import it.unisa.di.urcoach.model.service.PacchettoService;
import it.unisa.di.urcoach.model.service.PersonalTrainerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PacchettiControl.class)
@AutoConfigureMockMvc(addFilters = false)
class PacchettiControlTest {

    @MockBean
    PacchettoService pacchettoService;
    @MockBean
    CategoriaService categoriaService;
    @MockBean
    PersonalTrainerService personalTrainerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ricercaPacchetti() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        List<Pacchetto> pacchetti = new ArrayList<>();
        pacchetti.add(p1);
        when(pacchettoService.findByNome("mag")).thenReturn(pacchetti);
        mockMvc.perform(get("/api/pacchetti/mag"))
                .andDo(print())
                .andExpect(jsonPath("$[0].idPacchetto").value("1"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void filtriPacchetti_NoParam() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        List<Pacchetto> pacchetti = new ArrayList<>();
        pacchetti.add(p1);
        when(pacchettoService.findAll()).thenReturn(pacchetti);
        mockMvc.perform(get("/api/pacchettiFiltri")
                .param("nomeTrainer", "undefined")
                .param("categoria", "undefined")
                .param("prezzo", "undefined"))
                .andDo(print())
                .andExpect(jsonPath("$[0].idPacchetto").value("1"))
                .andExpect(jsonPath("$", hasSize(1)));
        verify(pacchettoService).findAll();
    }

    @Test
    void filtriPacchetti_NomeTrainer() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        List<Pacchetto> pacchetti = new ArrayList<>();
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        p1.setPersonalTrainer(a);
        pacchetti.add(p1);
        when(pacchettoService.findAll()).thenReturn(pacchetti);
        when(personalTrainerService.findByEmail("salviofasano@gmail.com")).thenReturn(a);
        mockMvc.perform(get("/api/pacchettiFiltri")
                .param("nomeTrainer", "salviofasano@gmail.com")
                .param("categoria", "undefined")
                .param("prezzo", "undefined"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(pacchettoService).findAll();
        verify(personalTrainerService).findByEmail("salviofasano@gmail.com");
    }

    @Test
    void filtriPacchetti_Categoria() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        Categoria c = new Categoria();
        c.setNome("Massa");
        p1.setCategoria(c);
        List<Pacchetto> pacchetti = new ArrayList<>();
        pacchetti.add(p1);
        when(pacchettoService.findAll()).thenReturn(pacchetti);
        when(categoriaService.findByNome("Massa")).thenReturn(c);
        mockMvc.perform(get("/api/pacchettiFiltri")
                .param("nomeTrainer", "undefined")
                .param("categoria", "Massa")
                .param("prezzo", "undefined"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(pacchettoService).findAll();
        verify(categoriaService).findByNome("Massa");
    }

    @Test
    void filtriPacchetti_Prezzo() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        p1.setCosto(500);
        Categoria c = new Categoria();
        c.setNome("Massa");
        p1.setCategoria(c);
        List<Pacchetto> pacchetti = new ArrayList<>();
        pacchetti.add(p1);
        when(pacchettoService.findAll()).thenReturn(pacchetti);
        mockMvc.perform(get("/api/pacchettiFiltri")
                .param("nomeTrainer", "undefined")
                .param("categoria", "undefined")
                .param("prezzo", "800"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(pacchettoService).findAll();
    }

    @Test
    void filtriPacchetti_All() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        Categoria c = new Categoria();
        c.setNome("Massa");
        p1.setCategoria(c);
        p1.setCosto(500);
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        p1.setPersonalTrainer(a);
        List<Pacchetto> pacchetti = new ArrayList<>();
        pacchetti.add(p1);
        when(personalTrainerService.findByEmail("salviofasano@gmail.com")).thenReturn(a);
        when(pacchettoService.findAll()).thenReturn(pacchetti);
        when(categoriaService.findByNome("Massa")).thenReturn(c);
        mockMvc.perform(get("/api/pacchettiFiltri")
                .param("nomeTrainer", "salviofasano@gmail.com")
                .param("categoria", "Massa")
                .param("prezzo", "800"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
        verify(pacchettoService).findAll();
        verify(categoriaService).findByNome("Massa");
        verify(personalTrainerService).findByEmail("salviofasano@gmail.com");
    }

    @Test
    void showPacchetti() throws Exception{
        mockMvc.perform(get("/pacchetti"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/pacchetti"))
                .andExpect(model().attributeExists("categorie", "trainers", "trainer", "atleta"));
    }

    @Test
    void showPacchetto() throws Exception{
        Categoria c = new Categoria();
        c.setNome("Massa");
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        a.setCognome("Fasano");
        a.setNome("Salvatore");
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        p1.setPersonalTrainer(a);
        p1.setCategoria(c);
        when(pacchettoService.findById(1)).thenReturn(p1);
        mockMvc.perform(get("/pacchetti/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/pacchetto"))
                .andExpect(model().attributeExists("p", "trainer", "atleta"))
                .andExpect(model().attribute("p", p1));
    }

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
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("trainer", trainer)
                .sessionAttr("logged", true)
                .param("azione", "nuovo"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/nuovoPacchetto"))
                .andExpect(model().attributeExists("pacchetto", "categorie"));
    }

    @Test
    void gestionePacchetti_AzioneEdit() throws Exception{
        Categoria c = new Categoria();
        c.setNome("Massa");
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        a.setCognome("Fasano");
        a.setNome("Salvatore");
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        p1.setPersonalTrainer(a);
        p1.setCategoria(c);
        when(pacchettoService.findById(1)).thenReturn(p1);
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("trainer", a)
                .sessionAttr("logged", true)
                .param("azione", "edit")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/nuovoPacchetto"))
                .andExpect(model().attributeExists("edit", "pacchetto", "categorie"));
    }

    @Test
    void gestionePacchetti_AzioneRimuovi() throws Exception{
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("trainer", a)
                .sessionAttr("logged", true)
                .param("azione", "rimuovi")
                .param("id", "1"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/areaPersonale/gestionePacchetti"));
    }

    @Test
    void gestionePacchetti_AzioneOther() throws Exception{
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        mockMvc.perform(get("/areaPersonale/gestionePacchetti")
                .sessionAttr("trainer", a)
                .sessionAttr("logged", true)
                .param("azione", "ciao")
                .param("id", "1"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void salvaPacchetto_Valid() throws Exception{
        Categoria c = new Categoria();
        c.setNome("Massa");
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        Pacchetto p = new Pacchetto("Pacchetto", 800, 3, new Date(Calendar.getInstance().getTime().getTime()), "Ciao io sono Lucia e sono una sirena");
        p.setPersonalTrainer(a);
        when(categoriaService.findByNome("Massa")).thenReturn(c);
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