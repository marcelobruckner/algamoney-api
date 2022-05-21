package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.repository.projection.ResumoLancamento;
import com.algaworks.algamoney.api.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
public class  LancamentoResource {

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping
    public Page<Lancamento> buscar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoService.filtrar(lancamentoFilter, pageable);
    }

    @GetMapping(params = "resumo")
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoService.resumir(lancamentoFilter, pageable);
    }

    @GetMapping("/{id}")
    public Lancamento buscar(@PathVariable Long id){
        return lancamentoService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Lancamento> adicionar(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        lancamentoService.delete(id);
    }
}
