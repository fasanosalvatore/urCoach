package it.unisa.di.urcoach.Control.Pacchetti;

import it.unisa.di.urcoach.Model.Entity.Atleta;
import it.unisa.di.urcoach.Model.Entity.Categoria;
import it.unisa.di.urcoach.Model.Entity.Pacchetto;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import it.unisa.di.urcoach.Model.Service.CategoriaService;
import it.unisa.di.urcoach.Model.Service.PacchettoService;
import it.unisa.di.urcoach.Model.Service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
