package it.unisa.di.urcoach.control.utenza;

import it.unisa.di.urcoach.control.pacchetti.PacchettiControl;
import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

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
@WebMvcTest(UtenzaControl.class)
@AutoConfigureMockMvc(addFilters = false)
class UtenzaControlTest {


    @MockBean
    PersonalTrainerService personalTrainerService;
    @MockBean
    FatturaService fatturaService;
    @MockBean
    AcquistoService acquistoService;
    @MockBean
    AtletaService atletaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login_Atleta() throws Exception{
        Atleta a = new Atleta();
        a.setEmail("salviofasano@gmail.com");
        a.setPassword("salvio");
        when(atletaService.checkUser("salviofasano@gmail.com", "salvio")).thenReturn(a);
        HttpSession session = mockMvc.perform(post("/login")
                .param("usernameL", "salviofasano@gmail.com")
                .param("passL", "salvio")
                .param("tipo", "Atleta"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andReturn().getRequest().getSession();
        assertTrue((Boolean)session.getAttribute("logged"));
        assertEquals((Atleta)session.getAttribute("atleta"), a);
    }

    @Test
    void login_PT() throws Exception{
        PersonalTrainer a = new PersonalTrainer();
        a.setEmail("salviofasano@gmail.com");
        a.setPassword("salvio");
        when(personalTrainerService.checkUser("salviofasano@gmail.com", "salvio")).thenReturn(a);
        HttpSession session = mockMvc.perform(post("/login")
                .param("usernameL", "salviofasano@gmail.com")
                .param("passL", "salvio")
                .param("tipo", "Trainer"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andReturn().getRequest().getSession();
        assertTrue((Boolean)session.getAttribute("logged"));
        assertEquals((PersonalTrainer)session.getAttribute("trainer"), a);
    }

    @Test
    void logout() {
    }

    @Test
    void registrazione() {
    }

    @Test
    void testRegistrazione() {
    }

    @Test
    void showAreaPersonale() {
    }

    @Test
    void showProfilo() {
    }

    @Test
    void modificaProfilo() {
    }

    @Test
    void aggiornaProfiloAtleta() {
    }

    @Test
    void aggiornaProfiloTrainer() {
    }

    @Test
    void showTrainers() {
    }

    @Test
    void showRecruiter() {
    }

    @Test
    void gestioneTrainer() {
    }

    @Test
    void mostraOrdini() {
    }
}