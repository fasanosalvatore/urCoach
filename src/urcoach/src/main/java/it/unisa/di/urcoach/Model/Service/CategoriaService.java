package it.unisa.di.urcoach.Model.Service;

import it.unisa.di.urcoach.Model.Entity.Categoria;

import java.util.List;

public interface CategoriaService {
    List<Categoria> findAll();
    Categoria findByNome(String nome);
}
