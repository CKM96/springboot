package text;

import config.TextConfig;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service //Another spring annotation to indicate that this class requires dependency injection
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TextService {

    private final TextConfig config;
    @Setter //Lombok anotation to generate a setter. The setter will only be used for testing.
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Take the given URL and post a GET request for it
     */
    public String getText(String type, String name) {
        String url = config.getUrl() + "/" + type + "/" + name;
        //Have our restTemplate (a client) send a get request to the webpage we're interested in.
        return restTemplate.getForObject(url, String.class);
    }

}
