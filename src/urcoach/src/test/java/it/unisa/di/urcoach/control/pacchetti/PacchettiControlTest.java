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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
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
    void filtriPacchetti() {
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
    void gestionePacchetti() {
    }

    @Test
    void salvaPacchetto() {
    }
}