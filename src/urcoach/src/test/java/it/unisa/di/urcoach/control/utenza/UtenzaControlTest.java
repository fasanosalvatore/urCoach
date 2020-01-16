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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.thymeleaf.exceptions.TemplateProcessingException;

import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.util.Calendar;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
        mockMvc.perform(get("/areaPersonale"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void showAreaPersonale_LoggedNoUser() throws Exception{
        mockMvc.perform(get("/areaPersonale")
                .sessionAttr("logged", true))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void showAreaPersonale_Atleta() throws Exception{
        Atleta a = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        mockMvc.perform(get("/areaPersonale")
                .sessionAttr("logged", true)
                .sessionAttr("atleta", a))
                .andExpect(status().isOk())
                .andExpect(view().name("View/ordini"))
                .andExpect(model().attributeExists("fatture"));
    }

    @Test
    void showAreaPersonale_Trainer() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        mockMvc.perform(get("/areaPersonale")
                .sessionAttr("logged", true)
                .sessionAttr("trainer", pt))
                .andExpect(status().isOk())
                .andExpect(view().name("View/vendite"))
                .andExpect(model().attributeExists("vendite"));
    }

    @Test
    void deleteProfilo_Atleta() throws Exception{
        Atleta a = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        mockMvc.perform(get("/areaPersonale/profilo")
                .sessionAttr("logged", true)
                .sessionAttr("atleta", a))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
        verify(atletaService).deleteByEmail("salvatore@gmail.com");
    }

    @Test
    void deleteProfilo_Trainer() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        mockMvc.perform(get("/areaPersonale/profilo")
                .sessionAttr("logged", true)
                .sessionAttr("trainer", pt))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
        verify(personalTrainerService).deleteByEmail("salvatore@gmail.com");
    }

    @Test
    void modificaProfilo_NoLogged() throws Exception{
        mockMvc.perform(get("/areaPersonale/modificaProfilo"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test()
    void modificaProfilo_Logged() throws Exception{
        assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/areaPersonale/modificaProfilo")
                    .sessionAttr("logged", true))
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrl("/"));
        });
    }

    @Test
    void modificaProfilo_Atleta() throws Exception{
        Atleta a = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        mockMvc.perform(get("/areaPersonale/modificaProfilo")
                .sessionAttr("logged", true)
                .sessionAttr("atleta", a))
                .andExpect(status().isOk())
                .andExpect(view().name("View/modificaProfilo"))
                .andExpect(model().attributeExists("atleta", "utente"));
    }

    @Test
    void modificaProfilo_Trainer() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        mockMvc.perform(get("/areaPersonale/modificaProfilo")
                .sessionAttr("logged", true)
                .sessionAttr("trainer", pt))
                .andExpect(status().isOk())
                .andExpect(view().name("View/modificaProfilo"))
                .andExpect(model().attributeExists("trainer", "utente"));
    }

    @Test
    void aggiornaProfiloAtleta_Correct() throws Exception{
        Atleta a = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        HttpSession session = mockMvc.perform(post("/areaPersonale/modificaProfilo/salvaAtleta")
                .flashAttr("utente", a))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/areaPersonale"))
                .andReturn().getRequest().getSession();
        assertEquals(a, (Atleta)session.getAttribute("atleta"));
    }

    @Test
    void aggiornaProfiloAtleta_Incorrect() throws Exception{
        Atleta cur = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        Atleta a = new Atleta("salvatore@gmail.com", "1", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        mockMvc.perform(post("/areaPersonale/modificaProfilo/salvaAtleta")
                .flashAttr("utente", a)
                .sessionAttr("logged", true)
                .sessionAttr("atleta", cur))
                .andExpect(status().isOk())
                .andExpect(view().name("View/modificaProfilo"))
                .andExpect(model().attributeExists("atleta", "utente"));
    }

    @Test
    void aggiornaProfiloTrainer_Correct() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        HttpSession session = mockMvc.perform(post("/areaPersonale/modificaProfilo/salvaTrainer")
                .flashAttr("utente", pt))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/areaPersonale"))
                .andReturn().getRequest().getSession();
        assertEquals(pt, (PersonalTrainer) session.getAttribute("trainer"));
    }

    @Test
    void aggiornaProfiloTrainer_Incorrect() throws Exception{
        PersonalTrainer cur = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 1);
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "1", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 1);
        mockMvc.perform(post("/areaPersonale/modificaProfilo/salvaTrainer")
                .flashAttr("utente", pt)
                .sessionAttr("logged", true)
                .sessionAttr("trainer", cur))
                .andExpect(status().isOk())
                .andExpect(view().name("View/modificaProfilo"))
                .andExpect(model().attributeExists("trainer", "utente"));
    }

    @Test
    void showTrainers() throws Exception{
        mockMvc.perform(get("/trainers"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/trainers"))
                .andExpect(model().attributeExists("trainers", "trainer", "atleta"));
    }

    @WithMockUser("Salvatore")
    @Test
    void showRecruiter() throws Exception{
        mockMvc.perform(get("/recruiter"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/admin/trainerAdmin"))
                .andExpect(model().attributeExists("trainers"));
    }

    @WithMockUser("Salvatore")
    @Test
    void gestioneTrainer_Other() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        when(personalTrainerService.findByEmail("salvatore@gmail.com")).thenReturn(pt);
        mockMvc.perform(get("/recruiter/gestioneTrainer")
                .param("email", "salvatore@gmail.com")
                .param("azione", "ciao"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/recruiter"));
    }

    @WithMockUser("Salvatore")
    @Test
    void gestioneTrainer_Verifica() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        when(personalTrainerService.findByEmail("salvatore@gmail.com")).thenReturn(pt);
        mockMvc.perform(get("/recruiter/gestioneTrainer")
                .param("email", "salvatore@gmail.com")
                .param("azione", "verifica"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/recruiter"));
        assertEquals(1, pt.getVerificato());
    }

    @WithMockUser("Salvatore")
    @Test
    void gestioneTrainer_Invalida() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 1);
        when(personalTrainerService.findByEmail("salvatore@gmail.com")).thenReturn(pt);
        mockMvc.perform(get("/recruiter/gestioneTrainer")
                .param("email", "salvatore@gmail.com")
                .param("azione", "invalida"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/recruiter"));
        assertEquals(0, pt.getVerificato());
    }

    @WithMockUser("Salvatore")
    @Test
    void gestioneTrainer_Rimuovi() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 1);
        when(personalTrainerService.findByEmail("salvatore@gmail.com")).thenReturn(pt);
        mockMvc.perform(get("/recruiter/gestioneTrainer")
                .param("email", "salvatore@gmail.com")
                .param("azione", "rimuovi"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/recruiter"));
        verify(personalTrainerService).deleteByEmail("salvatore@gmail.com");
    }

    @WithMockUser("Tonia")
    @Test
    void mostraOrdini() throws Exception{
        mockMvc.perform(get("/ordini"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/admin/acquistiAdmin"))
                .andExpect(model().attributeExists("acquisti"));
    }
}