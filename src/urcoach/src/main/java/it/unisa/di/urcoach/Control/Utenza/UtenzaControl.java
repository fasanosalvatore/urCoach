package it.unisa.di.urcoach.Control.Utenza;

import it.unisa.di.urcoach.Model.Entity.Acquisto;
import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import it.unisa.di.urcoach.Model.Service.*;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/ordini")
    public String mostraOrdini(Model model) {
        List<Acquisto> acquisti = acquistoService.findAll();
        model.addAttribute("acquisti", acquisti);
        return "View/admin/acquistiAdmin";
    }

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
}
