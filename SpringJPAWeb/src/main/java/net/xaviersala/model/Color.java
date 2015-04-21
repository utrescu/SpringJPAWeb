package net.xaviersala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Color {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;
  
  @NotNull 
  @NotEmpty
  protected String catala;
  
  @NotNull 
  @NotEmpty(message="{error.blanc}")
  protected String castella;
  
  @NotNull 
  @NotEmpty(message="{error.blanc}")
  protected String frances;
  
  @NotNull 
  @NotEmpty(message="{error.blanc}")
  protected String angles;
  
  public Color() {
    
  }
  
  public Color(String string, String string2, String string3, String string4) {
    catala = string;
    castella = string2;
    frances = string3;
    angles = string4;
  }
  
  public Long getId()
  {
     return this.id;
  }

  public void setId(final Long id)
  {
     this.id = id;
  } 
  
  /**
   * @return the catala
   */
  public String getCatala() {
    return catala;
  }
  /**
   * @param catala the catala to set
   */
  public void setCatala(String catala) {
    this.catala = catala;
  }
  /**
   * @return the castella
   */
  public String getCastella() {
    return castella;
  }
  /**
   * @param castella the castella to set
   */
  public void setCastella(String castella) {
    this.castella = castella;
  }
  /**
   * @return the frances
   */
  public String getFrances() {
    return frances;
  }
  /**
   * @param frances the frances to set
   */
  public void setFrances(String frances) {
    this.frances = frances;
  }
  /**
   * @return the angles
   */
  public String getAngles() {
    return angles;
  }
  /**
   * @param angles the angles to set
   */
  public void setAngles(String angles) {
    this.angles = angles;
  }
  
  /**
   * Converteix a minúscules
   */
  public void toLower() {
    catala = catala.toLowerCase();
    castella = castella.toLowerCase();
    frances = frances.toLowerCase();
    angles = angles.toLowerCase();
  }
  
  public String toString() {
    return catala + " " + castella + " " + frances + " " + angles;
  }
  
}
