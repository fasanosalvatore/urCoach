package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    List<Categoria> findAll();
    Categoria findByNome(String nome);
}
