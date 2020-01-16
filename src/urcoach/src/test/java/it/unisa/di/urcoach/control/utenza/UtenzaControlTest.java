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

import java.sql.Date;
import java.util.Calendar;

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
    void login_AtletaReg() throws Exception{
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
    void login_AtletaNoReg() throws Exception{
        when(atletaService.checkUser("salviofasano@gmail.com", "salvio")).thenReturn(null);
        mockMvc.perform(post("/login")
                .param("usernameL", "salviofasano@gmail.com")
                .param("passL", "salvio")
                .param("tipo", "Atleta"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/index"))
                .andExpect(model().attributeExists("errore", "atleta", "trainer"));
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
    void login_PTNoReg() throws Exception{
        when(personalTrainerService.checkUser("salviofasano@gmail.com", "salvio")).thenReturn(null);
        mockMvc.perform(post("/login")
                .param("usernameL", "salviofasano@gmail.com")
                .param("passL", "salvio")
                .param("tipo", "Trainer"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/index"))
                .andExpect(model().attributeExists("errore", "atleta", "trainer"));
    }

    @Test
    void logout() throws Exception{
        HttpSession session = mockMvc.perform(get("/logout")
                .sessionAttr("logged", true))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andReturn().getRequest().getSession(false);
        assertNull(session);
    }

    @Test
    void logout_NoLogged() throws Exception{
        HttpSession session = mockMvc.perform(get("/logout"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andReturn().getRequest().getSession(false);
        assertNull(session);
    }

    @Test
    void registrazioneTrainer_Correct() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        HttpSession session = mockMvc.perform(post("/registrazioneTrainer")
                .flashAttr("trainer", pt))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andReturn().getRequest().getSession();
        assertTrue((Boolean)session.getAttribute("logged"));
        assertEquals(pt, (PersonalTrainer)session.getAttribute("trainer"));
    }

    @Test
    void registrazioneTrainer_Incorrect() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "HJGHSVGSVGVCHSCBCKJBKSB", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        mockMvc.perform(post("/registrazioneTrainer")
                .flashAttr("trainer", pt))
                .andExpect(status().isOk())
                .andExpect(view().name("View/index"))
                .andExpect(model().attributeExists("trainer", "atleta"));
    }

    @Test
    void registrazioneAtleta_Correct() throws Exception{
        Atleta a = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        HttpSession session = mockMvc.perform(post("/registrazioneAtleta")
                .flashAttr("atleta", a))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andReturn().getRequest().getSession();
        assertTrue((Boolean)session.getAttribute("logged"));
        assertEquals(a, (Atleta)session.getAttribute("atleta"));
    }

    @Test
    void registrazioneAtleta_Incorrect() throws Exception{
        Atleta a = new Atleta("salvatore@gmail.com", "1", "Fasano", "FSHGDJDGHJJNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        mockMvc.perform(post("/registrazioneAtleta")
                .flashAttr("atleta", a))
                .andExpect(status().isOk())
                .andExpect(view().name("View/index"))
                .andExpect(model().attributeExists("trainer", "atleta"));
    }

    @Test
    void showAreaPersonale_NoLogged() throws Exception{
    }

    @Test
    void showProfilo() throws Exception{
    }

    @Test
    void modificaProfilo() throws Exception{
    }

    @Test
    void aggiornaProfiloAtleta() throws Exception{
    }

    @Test
    void aggiornaProfiloTrainer() throws Exception{
    }

    @Test
    void showTrainers() throws Exception{
    }

    @Test
    void showRecruiter() throws Exception{
    }

    @Test
    void gestioneTrainer() throws Exception{
    }

    @Test
    void mostraOrdini() throws Exception{
    }
}