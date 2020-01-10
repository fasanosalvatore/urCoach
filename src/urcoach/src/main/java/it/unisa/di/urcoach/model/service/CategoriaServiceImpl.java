package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Categoria;
import it.unisa.di.urcoach.model.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

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
