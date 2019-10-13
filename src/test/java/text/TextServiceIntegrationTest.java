package text;

import config.TextConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Standard integration test for TextService. Does no mocking and instead checks that the class behaves well with
 *  realistic data. Should really test negative/failure cases as well.
 */
public class TextServiceIntegrationTest {

    private TextService textService;

    /**
     * JUnit does not link up with Spring, so we can't use dependency injection. We therefore need to manually create
     *  our TextConfig.
     */
    @Before
    public void setUp() {
        TextConfig config = new TextConfig();
        config.setUrl("http://textfiles.com/etext/");

        textService = new TextService(config);
    }

    @Test
    public void test() {
        String text = textService.getText("NONFICTION", "analects");
        assertTrue(text.contains("I have talked with Hui for a whole day"));
    }
}