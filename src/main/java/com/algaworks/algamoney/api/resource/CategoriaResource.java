package com.algaworks.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.exceptionhandler.NegocioException;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

  @Autowired
  private CategoriaService categoriaService;

  @Autowired
  ApplicationEventPublisher publisher;

  @GetMapping
  public List<Categoria> listar() {
    return categoriaService.listar();
  }

  @GetMapping("/{id}")
  public Categoria buscar(@PathVariable Long id) {
    return categoriaService.buscarPorId(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Categoria> incluir(@Valid @RequestBody Categoria categoria, HttpServletResponse response)
      throws NegocioException {
    Categoria categoriaSalva = categoriaService.salvar(categoria);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

    return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
  }
}
