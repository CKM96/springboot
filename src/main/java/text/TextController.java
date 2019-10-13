package text;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Takes requests from the client, gets data from the service, possibly does something with it, then sends it back
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired) //Lombok will generate an autowired constructor for final variables
public class TextController {

    private final TextService textService;

    /**
     * Return the text at a given url, provided a valid GET request is received
     */
    @GetMapping("/text/{type}/{name}")
    public String text(
            @PathVariable("type") String type,
            @PathVariable("name") String name
    ) {
       return textService.getText(type, name);
    }

    /**
     * Return the number of times a given character appears in a particular text
     */
    @GetMapping("/text/{type}/{name}/count/{character}")
    public long countCharacter(
            @PathVariable("type") String type,
            @PathVariable("name") String name,
            @PathVariable("character") Character character
    ) {
        String text = text(type, name);
        return text.chars()
                //Passing in a lambda function as an argument here
                .mapToObj(t -> (char) t)
                //Object::method is a way of passing in a method as an argument
                .map(Character::toLowerCase)
                .filter(character::equals)
                .count();
    }

    /**
     * Returns a map of each character to how many time it appears in the text
     * Also handles any errors thrown by the text service, e.g. a 404, and gives some more info to the client
     */
    @GetMapping("/text/{type}/{name}/frequency")
    public Map<Character, Long> frequency(
            @PathVariable("type") String type,
            @PathVariable("name") String name
    ) {
        try {
            String text = text(type, name);
            return text.chars()
                    .mapToObj(t -> (char) t)
                    .map(Character::toLowerCase)
                    //groupingBy returns a map of keys t values, where the first function passed in defines the key
                    // and the second defines the value. See baeldung.com/java-groupingby-collector for more details
                    .collect(groupingBy(Function.identity(), counting()));
        }
        catch (Exception e) {
            throw new RuntimeException("Text not found. Ensure the type and name are correct.");
        }
    }
}
