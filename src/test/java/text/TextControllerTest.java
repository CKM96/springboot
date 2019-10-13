package text;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Standard unit test for TextController. Can use MockMvc (part of the spring framework starter test dependency) for
 *  controllers, which allows you to mock URL requests. Would ideally test negative/failure states also.
 */
@RunWith(MockitoJUnitRunner.class) //This class is deprecated, so should probably use an alternative, but it works here
public class TextControllerTest {

    private static final String TEXT = "The quick brown fox jumps over the lazy dog";
    private static final String TYPE = "type";
    private static final String NAME = "name";
    private static final Character CHAR = 'e';

    private MockMvc mockMvc;
    @Mock
    private TextService mockService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TextController(mockService)).build();
        when(mockService.getText(TYPE, NAME)).thenReturn(TEXT);
    }

    @Test
    public void testText() throws Exception {
        //Simulate a get request being sent to the mocked controller to test the Spring side of things
        MvcResult mvcResult = mockMvc.perform(get(String.format("/text/%s/%s", TYPE, NAME)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(200));
        assertThat(mvcResult.getResponse().getContentAsString(), is(TEXT));
    }

    @Test
    public void testCountCharacter() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(String.format("/text/%s/%s/count/%c", TYPE, NAME, CHAR)))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus(), is(200));
        assertThat(mvcResult.getResponse().getContentAsString(), is("3"));
    }

    @Test
    public void testFrequency() throws Exception {
        //We can use jsonPath to evaluate the return value of the frequency method. $.a will look at the top-level
        // key, 'a'.
        mockMvc.perform(get(String.format("/text/%s/%s/frequency", TYPE, NAME)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.t", is(2)))
                .andExpect(jsonPath("$.e", is(3)))
                .andExpect(jsonPath("$.z", is(1)))
                .andExpect(jsonPath("$.q", is(1)));
    }
}