/**
 * 
 */
package net.xaviersala.controller;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import net.xaviersala.controllers.ColorsController;
import net.xaviersala.exceptions.ColorNotFoundException;
import net.xaviersala.model.Color;
import net.xaviersala.repositories.ColorsRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

/**
 * @author xavier
 *
 */
@SuppressWarnings("unused")
public class ColorsControllerTest {
   
  ColorsController controller;
  ColorsRepository repository;
  List<Color> llistaColors;
  Color noExisteix;
  Color blau;
  
  @Before
  public void setUp() {
    
    blau = new Color("blau", "azul", "bleu", "blue");
    
    llistaColors = new ArrayList<Color>(); 
    llistaColors.add(new Color("vermell", "rojo", "rouge", "red"));
    llistaColors.add(new Color("groc", "amarillo", "jaune", "yellow"));
    llistaColors.add(new Color("verd", "verde", "vert", "green"));
    
    noExisteix = new Color("beix", "beis", "beige", "beige");
    repository = mock(ColorsRepository.class);     
    controller = new ColorsController(repository);
  }
  
  /**
   * Test method for {@link net.xaviersala.controllers.ColorsController#principal()}.
   */
  @Test
  public void testPrincipal() {
        
        assertEquals("index", controller.principal());
  }
  
  /**
   * Comprova que es carrega la vista principal al accedir a l'arrel.
   * 
   * @throws Exception
   */
  @Test
  public void testPaginaPrincipal() throws Exception {
    
    MockMvc mockMvc = standaloneSetup(controller).build();
    mockMvc.perform(get("/"))
           .andExpect(view().name("index"));
  }

  /**
   * Comprova que si el repositori torna un color aquest és 
   * renderitza en la vista correcta.
   * 
   * @throws Exception
   */
  @Test
  public void testMostraColorQueExisteix() throws Exception {
    
    when(repository.findByCatala(blau.getCatala())).thenReturn(blau);
    
    MockMvc mock = standaloneSetup(controller).build();
    mock.perform(get("/color/" + blau.getCatala()))
        .andExpect(view().name("mostra"))
        .andExpect(model().attributeExists("color"))
        .andExpect(model().attribute("color", blau));
  }
  
  
  /**
   * Comprova que si es demana un color que no existeix el resultat
   * és que acaba a /error.
   * 
   * @throws Exception
   */
  public void testMostraColorQueNoExisteix() throws Exception {
    
    when(repository.findByCatala(noExisteix.getCatala())).thenReturn(null);
    
    MockMvc mock = standaloneSetup(controller)        
       .build();
    mock.perform(get("/color/" + noExisteix.getCatala()))
        .andExpect(view().name("error"));
  }
  
  

  
  /**
   * Comprova que la llista surt encara que sigui en
   * blanc.
   * 
   */
  @Test
  public void testRetornaLaLlistaNull() throws Exception {
    
    List<Color> llistaColors = null; 
    
    
    when(repository.findAll()).thenReturn(llistaColors);
    
    MockMvc mock = standaloneSetup(controller).build();
    mock.perform(get("/colors"))
        .andExpect(view().name("llista"))
        .andExpect(status().isOk())        
        .andExpect(model().attributeDoesNotExist("colors"));
        //.andExpect(model().attribute("colors", null));
  }
  
  /**
   * Comprova que la llista té l'atribut amb els colors
   * que toquen.
   * 
   * @throws Exception
   */
  @Test
  public void testRetornaLaLlistaCorrecta() throws Exception {
    
        
    when(repository.findAll()).thenReturn(llistaColors);
    
    MockMvc mock = standaloneSetup(controller).build();
    mock.perform(get("/colors"))
        .andExpect(view().name("llista"))
        .andExpect(model().attributeExists("colors"))
        .andExpect(model().attribute("colors", hasItems(llistaColors.toArray())))
        .andExpect(model().attribute("colors", hasSize(llistaColors.size())))
        .andExpect(model().attribute("colors", hasItem(
                                     allOf( 
                                         hasProperty("catala", is(llistaColors.get(0).getCatala())),
                                         hasProperty("castella",is(llistaColors.get(0).getCastella())),
                                         hasProperty("frances",is(llistaColors.get(0).getFrances())),
                                         hasProperty("angles", is(llistaColors.get(0).getAngles()))
                                         )
            )))
        .andExpect(model().attribute("colors", hasItem(
                                     allOf( 
                                         hasProperty("catala", is(llistaColors.get(1).getCatala())),
                                         hasProperty("castella",is(llistaColors.get(1).getCastella())),
                                         hasProperty("frances",is(llistaColors.get(1).getFrances())),
                                         hasProperty("angles", is(llistaColors.get(1).getAngles()))
                                         )
            )))
        .andExpect(model().attribute("colors", hasItem(
                                     allOf( 
                                         hasProperty("catala", is(llistaColors.get(2).getCatala())),
                                         hasProperty("castella",is(llistaColors.get(2).getCastella())),
                                         hasProperty("frances",is(llistaColors.get(2).getFrances())),
                                         hasProperty("angles", is(llistaColors.get(2).getAngles()))
                                         )
            )));
    
    // Només s'ha de cridar la funció findAll() un cop...
    verify(repository, times(1)).findAll();
  }

  /**
   * Comprova que es mostra el formulari per donar
   * d'alta un color.
   * @throws Exception 
   */
  @Test
  public void mostraFormulari() throws Exception {
    
    MockMvc mockMvc = standaloneSetup(controller).build();
    mockMvc.perform(get("/crear"))
           .andExpect(view().name("formulari"));
  }
  
  /**
   * Donar d'alta un color correctament
   */
  @Test
  public void testAltaColor() throws Exception {
       
    MockMvc mockMvc = standaloneSetup(controller).build();
    mockMvc.perform(post("/crear")
           .param("catala", blau.getCatala())
           .param("castella", blau.getCastella())
           .param("frances", blau.getFrances())
           .param("angles", blau.getAngles()))
                      
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/color/" + blau.getCatala()));
            
  }
  
  /**
   * Comprova que la llista surt encara que sigui en
   * blanc.
   * 
   */
  @Test
  public void testAltaColorRepetit() throws Exception {

    when(repository.findByCatala(blau.getCatala())).thenReturn(blau);
    
    MockMvc mock = standaloneSetup(controller).build();
    mock.perform(post("/crear")
        .param("catala", blau.getCatala())
        .param("castella", blau.getCastella())
        .param("frances", blau.getFrances())
        .param("angles", blau.getAngles()))
        .andExpect(status().isNotAcceptable());
                
  }
  
  /**
   * Comprova que tots els colors hi són o bé que 
   * es torna al formulari...
   * 
   */
  @Test
  public void testAltaFaltenColors() throws Exception {

    MockMvc mock = standaloneSetup(controller).build();
    mock.perform(post("/crear")
           .param("catala", blau.getCatala())
           .param("castella", blau.getCastella()) 
           .param("frances", "")
           .param("angles", blau.getAngles()))      
           .andExpect(status().isOk())
           .andExpect(view().name("formulari"))
           .andExpect(model().errorCount(1));                
  }
  
//  @Test
//  public void submitError() throws Exception {
//    
//    MockMvc mockMvc = standaloneSetup(controller).build();
//    mockMvc.perform(
//        post("/crear"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("form"))
//        .andExpect(model().errorCount(2))
//        .andExpect(model().attributeHasFieldErrors("formBean", "name", "age"));
//  }

}
