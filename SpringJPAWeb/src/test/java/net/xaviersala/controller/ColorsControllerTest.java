/**
 * 
 */
package net.xaviersala.controller;

import static org.junit.Assert.*;
import net.xaviersala.controllers.ColorsController;
import net.xaviersala.repositories.ColorsRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author xavier
 *
 */
public class ColorsControllerTest {
   
  ColorsController controller;
  
  @Before
  public void setUp() {
    ColorsRepository repository = mock(ColorsRepository.class);     
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

  @Test
  public void testMostraColor() throws Exception {
    MockMvc mock = standaloneSetup(controller).build();
    mock.perform(get("/color/blau"))
        .andExpect(view().name("mostra"));
  }

}
