package it.unisa.di.urcoach.Model.Entity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrello {
    private Map<Integer, Pacchetto> pacchetti = new LinkedHashMap<>();

    public Collection<Pacchetto> getPacchetti() {
        return pacchetti.values();
    }

    public void put(Pacchetto p) {
        pacchetti.put(p.getIdPacchetto(), p);
    }

    public void remove(int idPacchetto) {
        pacchetti.remove(idPacchetto);
    }

    public double getPrezzoTot() {
        return pacchetti.values().stream().mapToDouble(p -> p.getCosto()).sum();
    }

    public int getNumeroPacchetti() {
        return pacchetti.size();
    }
}
