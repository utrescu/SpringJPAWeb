package net.xaviersala.repositories;

import java.util.List;

import net.xaviersala.model.Color;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ColorsRepository extends CrudRepository<Color, Long> {
  
  /**
   * Cerca si un color està en la llista
   * @param nom color a cercar
   * @return Entitat Color amb les dades
   */
  Color findByCatala(String nom);
  
  @Query("select u from Color u where u.castella like %?1")
  List<Color> findByCastellaEndsWith(String end);

}
