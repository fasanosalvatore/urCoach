package it.unisa.di.urcoach.control.utenza;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.service.AcquistoService;
import it.unisa.di.urcoach.model.service.AtletaService;
import it.unisa.di.urcoach.model.service.FatturaService;
import it.unisa.di.urcoach.model.service.PersonalTrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UtenzaControlIT {


    @Autowired
    PersonalTrainerService personalTrainerService;
    @Autowired
    FatturaService fatturaService;
    @Autowired
    AcquistoService acquistoService;
    @Autowired
    AtletaService atletaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login_AtletaReg() throws Exception{
        Atleta a = new Atleta();
        a.setEmail("salviofasano@gmail.com");
        a.setPassword("salvio");
        atletaService.save(a);
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
        HttpSession session = mockMvc.perform(post("/login")
                .param("usernameL", "ciao@ciao.it")
                .param("passL", "ciao")
                .param("tipo", "Trainer"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andReturn().getRequest().getSession();
        assertTrue((Boolean)session.getAttribute("logged"));
        PersonalTrainer a = personalTrainerService.findByEmail("ciao@ciao.it");
        assertEquals((PersonalTrainer)session.getAttribute("trainer"), a);
    }

    @Test
    void login_PTNoReg() throws Exception{
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
    void registrazioneTrainer_isPresent() throws Exception{
        PersonalTrainer ptnew = new PersonalTrainer("ciao@ciao.it", "FSNSVT98H02A024F", "Pasquale", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        HttpSession session = mockMvc.perform(post("/registrazioneTrainer")
                .flashAttr("trainer", ptnew))
                .andExpect(status().isOk())
                .andExpect(view().name("View/index"))
                .andExpect(model().attributeExists("trainer", "atleta"))
                .andReturn().getRequest().getSession();
        assertEquals(null, session.getAttribute("logged"));
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
    void registrazioneAtleta_isPresent() throws Exception{
        Atleta anew = new Atleta("ciao@sal.it", "Pasquale", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        HttpSession session = mockMvc.perform(post("/registrazioneAtleta")
                .flashAttr("atleta", anew))
                .andExpect(status().isOk())
                .andExpect(view().name("View/index"))
                .andExpect(model().attributeExists("trainer", "atleta"))
                .andReturn().getRequest().getSession();
        assertEquals(null, session.getAttribute("logged"));
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
    void deleteProfilo_NoLogged() throws Exception{
        mockMvc.perform(get("/areaPersonale/profilo"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void deleteProfilo_Other() throws Exception{
        mockMvc.perform(get("/areaPersonale/profilo")
                .sessionAttr("logged", true))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void deleteProfilo_Atleta() throws Exception{
        Atleta a = new Atleta("salvatore@gmail.com", "Salvatore", "Fasano", "FSNSVT98H02A024F", new Date(Calendar.getInstance().getTime().getTime()), "Via Benederro Cairoli 14", "Ciao1");
        atletaService.save(a);
        mockMvc.perform(get("/areaPersonale/profilo")
                .sessionAttr("logged", true)
                .sessionAttr("atleta", a))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void deleteProfilo_Trainer() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 0);
        personalTrainerService.save(pt);
        mockMvc.perform(get("/areaPersonale/profilo")
                .sessionAttr("logged", true)
                .sessionAttr("trainer", pt))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
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
        personalTrainerService.save(pt);
        mockMvc.perform(get("/recruiter/gestioneTrainer")
                .param("email", "salvatore@gmail.com")
                .param("azione", "verifica"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/recruiter"));
        assertEquals(1, personalTrainerService.findByEmail("salvatore@gmail.com").getVerificato());
    }

    @WithMockUser("Salvatore")
    @Test
    void gestioneTrainer_Invalida() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 1);
        personalTrainerService.save(pt);
        mockMvc.perform(get("/recruiter/gestioneTrainer")
                .param("email", "salvatore@gmail.com")
                .param("azione", "invalida"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/recruiter"));
        assertEquals(0, personalTrainerService.findByEmail("salvatore@gmail.com").getVerificato());
    }

    @WithMockUser("Salvatore")
    @Test
    void gestioneTrainer_Rimuovi() throws Exception{
        PersonalTrainer pt = new PersonalTrainer("salvatore@gmail.com", "FSNSVT98H02A024F", "Salvatore", "Fasano", new Date(Calendar.getInstance().getTime().getTime()), "Ciao1", "12345678901", "", 1);
        personalTrainerService.save(pt);
        mockMvc.perform(get("/recruiter/gestioneTrainer")
                .param("email", "salvatore@gmail.com")
                .param("azione", "rimuovi"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/recruiter"));
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