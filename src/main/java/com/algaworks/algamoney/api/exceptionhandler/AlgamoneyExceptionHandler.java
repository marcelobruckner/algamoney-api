package com.algaworks.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algamoney.api.service.exception.LancamentoBusinessExcepton;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    String mensagemUsuario = recuperarMensagem("mensagem.invalida");
    String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().getMessage() : ex.toString();

    Erro erro = new Erro(mensagemUsuario, mensagemDesenvolvedor);
    List<Erro> erros = List.of(erro);

    return handleExceptionInternal(ex, erros, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    List<Erro> erros = criarListaDeErros(ex.getBindingResult());

    return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(EntidadeNaoEncontradaException.class)
  public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

    String mensagemUsuario = obterMensagemPersonalizada(ex);
    String mensagemDesenvolvedor = ex.getMessage();
    Erro erro = new Erro(mensagemUsuario, mensagemDesenvolvedor);

    List<Erro> erros = List.of(erro);

    return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
    String mensagemUsuario = recuperarMensagem("recurso.operacao-nao-permitido");
    String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
    Erro erro = new Erro(mensagemUsuario, mensagemDesenvolvedor);

    List<Erro> erros = List.of(erro);

    return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<?> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

    String mensagemUsuario = recuperarMensagem("recurso.nao-encontrado");
    String mensagemDesenvolvedor = ex.toString();
    Erro erro = new Erro(mensagemUsuario, mensagemDesenvolvedor);

    List<Erro> erros = List.of(erro);

    return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(LancamentoBusinessExcepton.class)
  public ResponseEntity<Object> handleLancamentoBusinessExcepton(LancamentoBusinessExcepton ex, WebRequest request){
    String mensagemUsuario = recuperarMensagem("lancamento.business");
    String mensagemDesenvolvedor = ex.toString();
    Erro erro = new Erro(mensagemUsuario, mensagemDesenvolvedor);

    List<Erro> erros = List.of(erro);

    return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  private String obterMensagemPersonalizada(EntidadeNaoEncontradaException ex) {
    String mensagemUsuario = "";
    if(ex instanceof PessoaNaoEncontradaException){
      mensagemUsuario = recuperarMensagem("usuario.nao.encontrado");
    } else if (ex instanceof CategoriaNaoEncontradaException) {
      mensagemUsuario = recuperarMensagem("categoria.nao.encontrado");
    } else if (ex instanceof LancamentoNaoEncontradaException) {
      mensagemUsuario = recuperarMensagem("lancamento.nao.encontrado");
    }

    return mensagemUsuario;
  }

  private String recuperarMensagem(String code) {
    return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
  }

  private List<Erro> criarListaDeErros(BindingResult bindingResult) {
    List<Erro> erros = new ArrayList<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
      String mensagemDesenvolvedor = fieldError.toString();

      erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
    }
    return erros;
  }

  @Data
  @AllArgsConstructor
  public static class Erro {
    private String mensagemUsuario;
    private String mensagemDesenvolvedor;

  }
}
