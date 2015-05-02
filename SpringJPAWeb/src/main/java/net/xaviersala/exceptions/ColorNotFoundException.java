package net.xaviersala.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An example of an application-specific exception. Defined here for convenience
 * as we don't have a real domain model or its associated business logic.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Color no trobat")
public class ColorNotFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 4473989355699093545L;

  public ColorNotFoundException(String nom) {
    super(nom + " no trobat");
  }
  
}