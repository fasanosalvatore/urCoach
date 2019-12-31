package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Categoria;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import it.unisa.di.urcoach.Model.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findByNome(String nome) {
        Optional<Categoria> result = categoriaRepository.findById(nome);
        Categoria e = null;
        if (result.isPresent())
            e = result.get();
        return e;
    }
}
