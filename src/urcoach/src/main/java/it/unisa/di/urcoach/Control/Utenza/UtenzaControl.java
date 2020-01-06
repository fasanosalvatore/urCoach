package it.unisa.di.urcoach.Control.Utenza;

import it.unisa.di.urcoach.Model.Entity.Acquisto;
import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Entity.Fattura;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import it.unisa.di.urcoach.Model.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UtenzaControl {

    private final PacchettoService pacchettoService;
    private final CategoriaService categoriaService;
    private final PersonalTrainerService personalTrainerService;
    private final FatturaService fatturaService;
    private final AcquistoService acquistoService;
    private final AtletaService atletaService;

    public UtenzaControl(PacchettoService pacchettoService, CategoriaService categoriaService, PersonalTrainerService personalTrainerService, FatturaService fatturaService, AcquistoService acquistoService, AtletaService atletaService) {
        this.pacchettoService = pacchettoService;
        this.categoriaService = categoriaService;
        this.personalTrainerService = personalTrainerService;
        this.fatturaService = fatturaService;
        this.acquistoService = acquistoService;
        this.atletaService = atletaService;
    }

    /*****************************************
     * Mapping delle ricihieste di login/logout
     * e registrazione di personal trainer e atleta
     *****************************************/

    @PostMapping("/login")
    public String login(Model model, HttpServletRequest req){
        String email = req.getParameter("usernameL");
        String password = req.getParameter("passL");
        String tipo = req.getParameter("tipo");
        if(tipo.equals("Atleta")) {
            Atleta a = atletaService.checkUser(email, password);
            if (a != null) {
                req.getSession().setAttribute("logged", Boolean.valueOf(true));
                req.getSession().setAttribute("atleta", a);
                return "redirect:/";
            }
        } else {
            PersonalTrainer a = personalTrainerService.checkUser(email, password);
            if (a != null) {
                req.getSession().setAttribute("logged", Boolean.valueOf(true));
                req.getSession().setAttribute("trainer", a);
                return "redirect:/";
            }
        }
        model.addAttribute("errore", "Username o password errati");
        return "View/error";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest req) {
        if (req.getSession().getAttribute("logged") != null)
            req.getSession().removeAttribute("logged");
        req.getSession().invalidate();
        return "redirect:/";
    }

    @PostMapping("/registrazioneTrainer")
    public String registrazione(@ModelAttribute("trainer") PersonalTrainer trainer, BindingResult bindingResult, HttpServletRequest req) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "View/error";
        }
        //String nome = StringUtils.cleanPath(foto.getOriginalFilename());
        personalTrainerService.save(trainer);
        req.getSession().setAttribute("logged", Boolean.valueOf(true));
        req.getSession().setAttribute("trainer", trainer);
        return "redirect:/";
    }

    @PostMapping("/registrazioneAtleta")
    public String registrazione(@ModelAttribute("atleta") Atleta atleta, BindingResult bindingResult, HttpServletRequest req) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "View/error";
        }
        //String nome = StringUtils.cleanPath(foto.getOriginalFilename());
        atletaService.save(atleta);
        req.getSession().setAttribute("logged", Boolean.valueOf(true));
        req.getSession().setAttribute("atleta", atleta);
        return "redirect:/";
    }

    /*****************************************
     * Area personale utente
     *****************************************/

    @GetMapping("/areaPersonale")
    public String showAreaPersonale(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session.getAttribute("logged") != null){
            if(session.getAttribute("atleta") != null) {
                Atleta atleta = (Atleta) session.getAttribute("atleta");
                List<Fattura> fatture = fatturaService.findByAtleta(atleta);
                model.addAttribute("fatture", fatture);
                return "View/ordini";
            } else if(session.getAttribute("trainer") != null) {
                PersonalTrainer trainer = (PersonalTrainer) session.getAttribute("trainer");
                List<Acquisto> vendite = acquistoService.findByPersonalTrainer(trainer);
                model.addAttribute("vendite", vendite);
                return "View/vendite";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/areaPersonale/profilo")
    public String showProfilo(@RequestParam("azione") String azione, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session.getAttribute("logged") != null){
            if(session.getAttribute("atleta") != null) {
                Atleta atleta = (Atleta) session.getAttribute("atleta");
                if(azione.equals("rimuovi")) {
                    logout(model, req);
                    atletaService.deleteByEmail(atleta.getEmail());
                }
            } else if(session.getAttribute("trainer") != null) {
                PersonalTrainer trainer = (PersonalTrainer) session.getAttribute("trainer");
                if(azione.equals("rimuovi")) {
                    logout(model, req);
                    personalTrainerService.deleteByEmail(trainer.getEmail());
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/areaPersonale/modificaProfilo")
    public String modificaProfilo(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session.getAttribute("logged") != null){
            if(session.getAttribute("trainer") != null) {
                PersonalTrainer pt = (PersonalTrainer) session.getAttribute("trainer");
                model.addAttribute("trainer", true);
                model.addAttribute("utente", pt);
            } else if (session.getAttribute("atleta") != null) {
                model.addAttribute("atleta", true);
                Atleta a = (Atleta) session.getAttribute("atleta");
                model.addAttribute("utente", a);
            }
            return "View/modificaProfilo";
        }
        return "redirect:/";
    }

    @PostMapping("/areaPersonale/modificaProfilo/salvaAtleta")
    public String aggiornaProfiloAtleta(@ModelAttribute("utente") Atleta atleta, Model model, HttpServletRequest req) {
        atleta.getFatture().clear();
        atleta.getFatture().addAll(new ArrayList<Fattura>());
        atletaService.save(atleta);
        req.getSession().setAttribute("atleta", atleta);
        return "redirect:/areaPersonale";
    }

    @PostMapping("/areaPersonale/modificaProfilo/salvaTrainer")
    public String aggiornaProfiloTrainer(@ModelAttribute("utente") PersonalTrainer pt, Model model, HttpServletRequest req) {
        pt.getPacchettiCreati().clear();
        pt.getPacchettiCreati().addAll(new ArrayList<>());
        personalTrainerService.save(pt);
        req.getSession().setAttribute("trainer", pt);
        return "redirect:/areaPersonale";
    }

    /*****************************************
     * Visualizzazione pagina personal trainer
     *****************************************/

    @GetMapping("/trainers")
    public String showTrainers(Model model) {
        List<PersonalTrainer> trainers= personalTrainerService.findByVerificato(1);
        model.addAttribute("trainers", trainers);
        PersonalTrainer trainer = new PersonalTrainer();
        Atleta atleta = new Atleta();
        model.addAttribute("trainer", trainer);
        model.addAttribute("atleta", atleta);
        return "View/trainers";
    }

    /*****************************************
     * Gestione dei trainer da parte del recruiter
     *****************************************/

    @GetMapping("/recruiter")
    public String showRecruiter(Model model) {
        List<PersonalTrainer> trainers = personalTrainerService.findAll();
        model.addAttribute("trainers", trainers);
        return "View/admin/trainerAdmin.html";
    }

    @GetMapping("/recruiter/gestioneTrainer")
    public String gestioneTrainer(@RequestParam("email") String emailTrainer, @RequestParam("azione") String azione, Model model) {
        PersonalTrainer pt = personalTrainerService.findByEmail(emailTrainer);
        if(azione.equals("verifica")) {
            pt.setVerificato(1);
            personalTrainerService.save(pt);
        }
        else if(azione.equals("invalida")) {
            pt.setVerificato(0);
            personalTrainerService.save(pt);
        }
        else if(azione.equals("rimuovi")) personalTrainerService.deleteByEmail(emailTrainer);
        return "redirect:/recruiter";
    }

    /*****************************************
     * Gestione degli ordini
     *****************************************/

    @GetMapping("/ordini")
    public String mostraOrdini(Model model) {
        List<Acquisto> acquisti = acquistoService.findAll();
        model.addAttribute("acquisti", acquisti);
        return "View/admin/acquistiAdmin";
    }

}
