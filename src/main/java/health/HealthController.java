package health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A rest controller class used to test pinging the webserver.
 */
@RestController
public class HealthController {

    @GetMapping("/ping")
    public String ping() {
        return "Hello world!";
    }

}
