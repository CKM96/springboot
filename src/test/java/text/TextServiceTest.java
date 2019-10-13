package text;

import config.TextConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Standard unit test class for TextService. Mocks everything around the general flow, i.e. the restTemplate.
 */
@RunWith(MockitoJUnitRunner.class) //Needed for the @Mock annotation below to work
public class TextServiceTest {

    private static final String URL = "url";
    private static final String TYPE = "type";
    private static final String NAME = "name";

    private TextService textService;
    @Mock //Mockito annotation. Could instead set mockTemplate = mock(RestTemplate.class) in setUp(). Either works.
    private RestTemplate mockTemplate;

    /**
     * Set up all of our mock data before actually testing flows
     */
    @Before
    public void setUp() {
        TextConfig mockConfig = new TextConfig();
        mockConfig.setUrl(URL);

        textService = new TextService(mockConfig);

        textService.setRestTemplate(mockTemplate);
    }

    /**
     * The Test annotation means that JUnit will run this method as a test through IntelliJ.
     */
    @Test
    public void testGetText() {
        String result = "result";
        //Mockito allows us to intercept a method call with particular hard-coded arguments and return what we tell it to
        when(mockTemplate.getForObject(String.format("%s/%s/%s", URL, TYPE, NAME), String.class)).thenReturn(result);

        assertThat(textService.getText(TYPE, NAME), is(result));
    }

    /**
     * The Ignore annotation means that this test will be ignored if the entire test class is run. It can still be run
     *  manually.
     */
    @Test
    @Ignore
    public void testWillFail() {
        assertThat(1, is(2));
    }
}