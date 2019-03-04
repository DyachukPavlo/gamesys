package gamesys.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
@Rollback
@ContextConfiguration(classes = {},
        initializers = {ConfigFileApplicationContextInitializer.class})
public class NewsCtrlTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getAllNews() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/news/all")).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void getRecentNews() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/news/recent").param("quantity","1")).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void wrongEndpoint() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/jokes/wrong")).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }
}