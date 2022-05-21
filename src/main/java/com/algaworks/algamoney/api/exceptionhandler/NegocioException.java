package com.algaworks.algamoney.api.exceptionhandler;

public class NegocioException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public NegocioException(String message) {
    super(message);
  }

  public NegocioException(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }
}
