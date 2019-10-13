package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Boots up the webserver
 */
@SpringBootApplication
@ComponentScan(basePackages = {"health", "config", "text"}) //Indicates which packages may contain beans, components,
                                                            // controllers, etc. that may need autowiring
public class Application {

    public static void main(String[] args) {
        System.out.println("Loading up spring boot");
        SpringApplication.run(Application.class, args);
    }


}
