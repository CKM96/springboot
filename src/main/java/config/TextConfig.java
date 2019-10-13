package config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data //A lombok annotation: indicates that this class is a POJO (Plain Old Java Object), i.e. it just has variables
      // Adding this notation will allow lombok to automatically generate getters and setters.
//Spring annotations used for dependency injection. Indicate that the url variable is set in the application.yml file.
@Component
@ConfigurationProperties("text")
public class TextConfig {
    private String url;
}
