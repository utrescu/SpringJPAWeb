package net.xaviersala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Inicia el programa.
 * 
 * Spring Boot detecta que en el directory 'resources' hi ha un
 * fitxer SQL que s'executarà automàticament cada cop que 
 * s'inicia la base de dades.
 * 
 * @author xavier
 *
 */
@SpringBootApplication
public class SpringJpaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaWebApplication.class, args);
    }
}
