package it.unisa.di.urcoach.Control.Carrello;

import it.unisa.di.urcoach.Model.Entity.*;
import it.unisa.di.urcoach.Model.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CarrelloControl {
    private final PacchettoService pacchettoService;
    private final CategoriaService categoriaService;
    private final PersonalTrainerService personalTrainerService;
    private final FatturaService fatturaService;
    private final AcquistoService acquistoService;
    private final AtletaService atletaService;

    public CarrelloControl(PacchettoService pacchettoService, CategoriaService categoriaService, PersonalTrainerService personalTrainerService, FatturaService fatturaService, AcquistoService acquistoService, AtletaService atletaService) {
        this.pacchettoService = pacchettoService;
        this.categoriaService = categoriaService;
        this.personalTrainerService = personalTrainerService;
        this.fatturaService = fatturaService;
        this.acquistoService = acquistoService;
        this.atletaService = atletaService;
    }

    @PostMapping("/carrello")
    public String aggiungiCarrello(@RequestParam("idPacchetto") int idPacchetto, @RequestParam("azione") String azione, Model model, HttpServletRequest req) {
        Carrello carrello = (Carrello) req.getSession().getAttribute("carrello");
        if (carrello == null) carrello = new Carrello();
        if (azione.equals("aggiungi")) carrello.put(pacchettoService.findById(idPacchetto));
        else if (azione.equals("rimuovi")) carrello.remove(idPacchetto);
        req.getSession().setAttribute("carrello", carrello);
        req.getSession().setAttribute("nCarrello", carrello.getNumeroPacchetti());
        model.addAttribute("pacchetti", carrello.getPacchetti());
        PersonalTrainer trainer = new PersonalTrainer();
        Atleta atleta = new Atleta();
        model.addAttribute("trainer", trainer);
        model.addAttribute("atleta", atleta);
        return "View/carrello";
    }

    @GetMapping("/carrello")
    public String mostraCarrello(Model model, HttpServletRequest req) {
        Carrello carrello = (Carrello) req.getSession().getAttribute("carrello");
        if (carrello == null) {
            carrello = new Carrello();
            req.getSession().setAttribute("carrello", carrello);
            req.getSession().setAttribute("nCarrello", carrello.getNumeroPacchetti());
        }
        model.addAttribute("pacchetti", carrello.getPacchetti());
        PersonalTrainer trainer = new PersonalTrainer();
        Atleta atleta = new Atleta();
        model.addAttribute("trainer", trainer);
        model.addAttribute("atleta", atleta);
        return "View/carrello";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpServletRequest req) {
        Atleta atletaAcquisto = (Atleta) req.getSession().getAttribute("atleta");
        if(atletaAcquisto == null) return "View/Error";
        Carrello carrello = (Carrello) req.getSession().getAttribute("carrello");
        model.addAttribute("pacchetti", carrello.getPacchetti());
        PersonalTrainer trainer = new PersonalTrainer();
        Atleta atleta = new Atleta();
        model.addAttribute("trainer", trainer);
        model.addAttribute("atleta", atleta);
        return "View/checkout";
    }

    @PostMapping("/nuovaFattura")
    public String nuovaFattura(@RequestParam("costo") float costo, @RequestParam("atleta") String email, Model model, HttpServletRequest req) {
        Fattura fattura = new Fattura(costo, atletaService.findByEmail(email));
        fatturaService.save(fattura);
        Carrello carrello = (Carrello)req.getSession().getAttribute("carrello");
        for(Pacchetto p : carrello.getPacchetti()) {
            acquistoService.save(new Acquisto(fattura, p, p.getCosto()));
        }
        carrello.getPacchetti().clear();
        return "redirect:/areaPersonale";
    }
}
