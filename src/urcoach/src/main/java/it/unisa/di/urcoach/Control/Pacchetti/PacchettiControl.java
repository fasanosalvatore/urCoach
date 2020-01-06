package it.unisa.di.urcoach.Control.Pacchetti;

import it.unisa.di.urcoach.Model.Entity.*;
import it.unisa.di.urcoach.Model.Service.CategoriaService;
import it.unisa.di.urcoach.Model.Service.PacchettoService;
import it.unisa.di.urcoach.Model.Service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PacchettiControl {

    private final PacchettoService pacchettoService;
    private final CategoriaService categoriaService;
    private final PersonalTrainerService personalTrainerService;

    public PacchettiControl(PacchettoService pacchettoService, CategoriaService categoriaService, PersonalTrainerService personalTrainerService) {
        this.pacchettoService = pacchettoService;
        this.categoriaService = categoriaService;
        this.personalTrainerService = personalTrainerService;
    }

    @GetMapping("/api/pacchetti/{nome}")
    @ResponseBody
    public List<Pacchetto> ricercaPacchetti(@PathVariable("nome") String nome) {
        return pacchettoService.findByNome(nome);
    }

    @GetMapping("/api/pacchettiFiltri")
    @ResponseBody
    public List<Pacchetto> filtriPacchetti(@RequestParam(required = false) String nomeTrainer, @RequestParam(required = false) String categoria, @RequestParam(required = false) String prezzo) {
        List<Pacchetto> pacchetti= pacchettoService.findAll();
        if (!nomeTrainer.equals("undefined")) {
            PersonalTrainer pt = personalTrainerService.findByEmail(nomeTrainer);
            pacchetti = pacchetti.stream().filter(p -> p.getPersonalTrainer().equals(pt)).collect(Collectors.toList());
        }
        if (!categoria.equals("undefined")) {
            Categoria cat = categoriaService.findByNome(categoria);
            pacchetti = pacchetti.stream().filter(p -> p.getCategoria().equals(cat)).collect(Collectors.toList());
        }
        if (!prezzo.equals("undefined")) {
            int c = Integer.parseInt(prezzo);
            pacchetti = pacchetti.stream().filter(p -> p.getCosto() < c).collect(Collectors.toList());
        }
        return pacchetti;
    }

    @GetMapping("/pacchetti")
    public String showPacchetti(Model model) {
        List<Categoria> categorie = categoriaService.findAll();
        List<PersonalTrainer> trainers = personalTrainerService.findAll();
        model.addAttribute("categorie", categorie);
        model.addAttribute("trainers", trainers);
        PersonalTrainer trainer = new PersonalTrainer();
        Atleta atleta = new Atleta();
        model.addAttribute("trainer", trainer);
        model.addAttribute("atleta", atleta);
        return "View/pacchetti";
    }

    @GetMapping(value = "/pacchetti/{id}")
    public String showPacchetto(@PathVariable("id") int id, Model model) {
        Pacchetto pacchetto = pacchettoService.findById(id);
        model.addAttribute("p", pacchetto);
        PersonalTrainer trainer = new PersonalTrainer();
        Atleta atleta = new Atleta();
        model.addAttribute("trainer", trainer);
        model.addAttribute("atleta", atleta);
        return "View/pacchetto";
    }

    /*********************
     * Gestione pacchetti
     *********************/

    @GetMapping("/areaPersonale/gestionePacchetti")
    public String gestionePacchetti(@RequestParam(value = "azione", required = false) String azione, @RequestParam(value = "id", required = false) Integer id, Model model, HttpServletRequest req) {
        if(req.getSession().getAttribute("trainer") != null) {
            PersonalTrainer pt = (PersonalTrainer) req.getSession().getAttribute("trainer");
            if (azione == null) {
                List<Pacchetto> pacchetti = pacchettoService.findAllByPersonalTrainer(pt);
                model.addAttribute("pacchetti", pacchetti);
                return "View/gestionePacchetti";
            } else if (azione.equals("nuovo")) {
                Pacchetto pacchetto = new Pacchetto();
                model.addAttribute("pacchetto", pacchetto);
                List<Categoria> categorie = categoriaService.findAll();
                model.addAttribute("categorie", categorie);
                return "View/nuovoPacchetto";
            } else if (azione.equals("edit")) {
                model.addAttribute("edit", true);
                Pacchetto pacchetto = pacchettoService.findById(id);
                model.addAttribute("pacchetto", pacchetto);
                List<Categoria> categorie = categoriaService.findAll();
                model.addAttribute("categorie", categorie);
                return "View/nuovoPacchetto";
            } else if (azione.equals("rimuovi")) {
                pacchettoService.deleteById(id);
                return "redirect:/areaPersonale/gestionePacchetti";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/areaPersonale/gestionePacchetti/salva")
    public String salvaPacchetto(@ModelAttribute("pacchetto") Pacchetto pacchetto, @RequestParam(value = "categoria", required = false) String categoria, Model model, HttpServletRequest req) {
        if(req.getSession().getAttribute("trainer") != null) {
            PersonalTrainer pt = (PersonalTrainer) req.getSession().getAttribute("trainer");
            pacchetto.setPersonalTrainer(pt);
            pacchetto.setCategoria(categoriaService.findByNome(categoria));
            //clear existing children list so that they are removed from database
            pacchetto.getAcquisti().clear();
            pacchetto.getAcquisti().addAll(new ArrayList<Acquisto>());
            pacchetto.setDataCreazione(new Date(Calendar.getInstance().getTime().getTime()));
            pacchettoService.save(pacchetto);
            return "redirect:/areaPersonale/gestionePacchetti";
        }
        return "redirect:/";
    }
}
