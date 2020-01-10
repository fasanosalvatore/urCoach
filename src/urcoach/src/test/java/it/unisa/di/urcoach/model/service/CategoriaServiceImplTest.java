package it.unisa.di.urcoach.model.service;

import it.unisa.di.urcoach.model.entity.Categoria;
import it.unisa.di.urcoach.model.repository.CategoriaRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceImplTest {

    CategoriaRepository categoriaRepository;
    CategoriaService categoriaService = new CategoriaServiceImpl(categoriaRepository);

    @BeforeEach
    public void init() {
        categoriaRepository = mock(CategoriaRepository.class);
        categoriaService = new CategoriaServiceImpl(categoriaRepository);
    }

    @Test
    void findAll() {
        List<Categoria> categorie = new ArrayList<>();
        Categoria cat1 = new Categoria();
        Categoria cat2 = new Categoria();
        categorie.add(cat1);
        categorie.add(cat2);
        when(categoriaRepository.findAll()).thenReturn(categorie);
        List<Categoria> found = categoriaService.findAll();
        assertEquals(found.size(), 2);
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void findByNome() {
        Categoria cat = new Categoria();
        cat.setNome("Definizione");
        when(categoriaRepository.findById("Definizione")).thenReturn(java.util.Optional.of(cat));
        Categoria found = categoriaService.findByNome("Definizione");
        assertEquals(found.getNome(), "Definizione");
        verify(categoriaRepository).findById("Definizione");
    }
}