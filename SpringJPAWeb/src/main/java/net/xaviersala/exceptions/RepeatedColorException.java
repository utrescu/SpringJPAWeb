package net.xaviersala.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Color repetit")
public class RepeatedColorException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 4473989355699093545L;

  public RepeatedColorException(String nom) {
    super(nom + " repetit");
  }
}
