package net.xaviersala.controllers;

import java.util.List;

import javax.validation.Valid;

import net.xaviersala.exceptions.ColorNotFoundException;
import net.xaviersala.exceptions.RepeatedColorException;
import net.xaviersala.model.Color;
import net.xaviersala.repositories.ColorsRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ColorsController {

  private static final Log log = LogFactory.getLog(ColorsController.class);

  /**
   * El servei que genera objectes Pinta.
   */
  ColorsRepository colorsRepository;


//  /**
//   * Automàticament Spring se n'encarregarà d'emplenar el servei.
//   *
//   * @param colorsRepository Obtenir el repositori de forma automàtica
//   */
  @Autowired
  public ColorsController(ColorsRepository colorsRepository) {
    // Assert.notNull(repository, "Repository must not be null!");
    this.colorsRepository = colorsRepository;
  }

  
  /**
   * 
   */
  @RequestMapping("/")
  public String principal() {
    return "index";
  }
  
  /**
   * La URL /color retorna un objecte Pinta.
   *
   * @return Objecte Pinta en XML
   */
  @RequestMapping(value = "/colors")
  public String generaColors(Model model) {
    
    List<Color> colors = (List<Color>) colorsRepository.findAll();
    log.info("Algú demana tots els colors" + colors);
    model.addAttribute("colors", colors);
    return "llista";
  }

  /**
   * Comprova si un color existeix o no i el passa a la vista anomenada
   * 'mostra'.
   * @param nom nom del color a cercar
   * @return El color o null
   */
  @RequestMapping(value= "/color/{nom}")
  public String buscaColor(@PathVariable("nom") String nom, Model model) {
    log.info("Cercant el color " + nom );
    Color color = colorsRepository.findByCatala(nom);
    
    if (color == null) throw new ColorNotFoundException(nom);;
    
    model.addAttribute("color", color);
    return "mostra";
  }
  
  /**
   * Formulari de donar d'alta un nou color
   * 
   */
  @RequestMapping(value="/crear", method=RequestMethod.GET)
  public String creaFormulariColor(Model model) {
    model.addAttribute("color", new Color());
    return "formulari";
  }
  
  /**
   * Crea el color
   * @return Si ha anat o no
   */
  @RequestMapping(value="/crear", method=RequestMethod.POST)
  public String creaColor(@Valid Color color, BindingResult resultat, Model model) {
    
    color.toLower();
    // El formulari valida
    if (resultat.hasErrors()) {      
      return "formulari";
    }
  
    // Comprova si ja hi era
    if (colorsRepository.findByCatala(color.getCatala()) != null) {
      
       // model.addAttribute("missatgeError", true);
       // return "formulari";
        throw new RepeatedColorException(color.getCatala());
    }
    
    colorsRepository.save(color);
    return "redirect:/color/" + color.getCatala();
  }
  
}
