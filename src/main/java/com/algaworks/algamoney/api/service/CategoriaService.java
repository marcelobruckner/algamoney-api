package com.algaworks.algamoney.api.service;

import java.util.List;

import com.algaworks.algamoney.api.exceptionhandler.CategoriaNaoEncontradaException;
import com.algaworks.algamoney.api.exceptionhandler.NegocioException;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

  @Autowired
  private CategoriaRepository repository;

  public Categoria buscarPorId(Long id) {
    return repository.findById(id).orElseThrow(
            () -> new CategoriaNaoEncontradaException(id));
  }

  public List<Categoria> listar() {
    return repository.findAll();
  }

  public Categoria salvar(Categoria categoria) throws NegocioException {
    Categoria categoriaExisitente = repository.findByNome(categoria.getNome());
    if (categoriaExisitente == null) {
      return repository.save(categoria);
    } else {
      throw new NegocioException("Categoria " + categoria.getNome() + " ja existe");
    }

  }
}
