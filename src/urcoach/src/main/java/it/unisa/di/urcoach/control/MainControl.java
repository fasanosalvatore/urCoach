package it.unisa.di.urcoach.control;

import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.Carrello;
import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.entity.PersonalTrainer;
import it.unisa.di.urcoach.model.service.AtletaService;
import it.unisa.di.urcoach.model.service.PacchettoService;
import it.unisa.di.urcoach.model.service.PersonalTrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainControl {

    private final PacchettoService pacchettoService;
    private final Carrello carrello = new Carrello();

    public MainControl(PacchettoService pacchettoService) {
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
