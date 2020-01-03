package it.unisa.di.urcoach.Control;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Entity.Carrello;
import it.unisa.di.urcoach.Model.Entity.Pacchetto;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import it.unisa.di.urcoach.Model.Service.AtletaService;
import it.unisa.di.urcoach.Model.Service.PacchettoService;
import it.unisa.di.urcoach.Model.Service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainControl {

    private final AtletaService atletaService;
    private final PersonalTrainerService personalTrainerService;
    private final PacchettoService pacchettoService;
    private final Carrello carrello = new Carrello();

    public MainControl(AtletaService atletaService, PersonalTrainerService personalTrainerService, PacchettoService pacchettoService) {
        this.atletaService = atletaService;
        this.personalTrainerService = personalTrainerService;
        this.pacchettoService = pacchettoService;
    }


    @GetMapping("/")
    public String getHome(Model model, HttpServletRequest req) {
        List<Pacchetto> ultimiPacchetti = pacchettoService.findLast();
        model.addAttribute("ultimiPacchetti", ultimiPacchetti);
        PersonalTrainer trainer = new PersonalTrainer();
        Atleta atleta = new Atleta();
        model.addAttribute("trainer", trainer);
        model.addAttribute("atleta", atleta);
        if(req.getSession().getAttribute("carrello") == null) {
            req.getSession().setAttribute("carrello", carrello);
            req.getSession().setAttribute("nCarrello", 0);
        }
        return "View/index";
    }
}
