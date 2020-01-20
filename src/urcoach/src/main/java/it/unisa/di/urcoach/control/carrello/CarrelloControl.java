package it.unisa.di.urcoach.control.carrello;

import it.unisa.di.urcoach.model.entity.*;
import it.unisa.di.urcoach.model.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Metodo utilizzato per aggiungere/rimuovere elementi dal carrello
     * @param idPacchetto id del pacchetto da aggiungere al carrello
     * @param azione azione che bisgona svolgere, si può trattare di aggiunta o modifica
     * @param model
     * @param req occorre per accedere ai dati dalla sessione
     * @return una pagina con il carrello aggiornato
     */
    @PostMapping("/carrello")
    public String gestioneCarrello(@RequestParam("idPacchetto") int idPacchetto, @RequestParam("azione") String azione, Model model, HttpServletRequest req) {
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

    /**
     * Visualizzazione del carrello
     * @param model
     * @param req occorre per accedere ai dati dalla sessione
     * @return una pagina con il carrello aggiornato
     */
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

    /**
     * Inizio della procedura di checkout
     * @param model
     * @param req
     * @return una opagina in cui visualizzare la fattura ed aggiungere il metodo di pagamento
     */
    @GetMapping("/checkout")
    public String checkout(Model model, HttpServletRequest req) {
        Atleta atletaAcquisto = (Atleta) req.getSession().getAttribute("atleta");
        if(atletaAcquisto == null) return "redirect:/carrello#login";
        Carrello carrello = (Carrello) req.getSession().getAttribute("carrello");
        model.addAttribute("pacchetti", carrello.getPacchetti());
        PersonalTrainer trainer = new PersonalTrainer();
        Atleta atleta = new Atleta();
        model.addAttribute("trainer", trainer);
        model.addAttribute("atleta", atleta);
        return "View/checkout";
    }

    /**
     * Dopo avere inserito la propria carta di credito viene salvata la fattura nel database e con essa tutti i singoli acquisiti dell'utente
     * @param costo valore totale della fattura
     * @param email email dell'atleta che ha effettuato il pagamento
     * @param model
     * @param req occorre per accedere ai dati dalla sessione
     * @return visualizza l'area personale dell'utente
     */
    @PostMapping("/nuovaFattura")
    public String nuovaFattura(@RequestParam("cc") String cc, @RequestParam("dataScadenza") String dataScadenza, @RequestParam("cvc") String cvc, @RequestParam("costo") float costo, @RequestParam("atleta") String email, RedirectAttributes redirectAttributes, Model model, HttpServletRequest req) {
        List<String> errori = new ArrayList<>();
        if (!cc.matches("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$")){
            errori.add("Il numero della carta di credito non rispetta il formato");
            redirectAttributes.addFlashAttribute("errori", errori);
        }
        if (!dataScadenza.matches("^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$")){
            errori.add("La data di scadenza non rispetta il formato");
            redirectAttributes.addFlashAttribute("errori", errori);
        }
        if (!cvc.matches("^[0-9]{3,4}$")){
            errori.add("Il cvc della carta di credito non rispetta il formato");
            redirectAttributes.addFlashAttribute("errori", errori);
        }
        if (errori.size()>0) return "redirect:/checkout";
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
