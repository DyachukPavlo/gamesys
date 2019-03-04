package gamesys.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
@Rollback
public class JokeCtrlTest {
    private MockMvc mockMvc;
    @TestConfiguration
    static class JokeServiceConfig {
        @Autowired
        EntityManager entityManager;
    }

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    public void uploadFile() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/jokes/all")).andExpect(status().isOk()).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void getRecentNews() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/jokes/recent").param("quantity","1")).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void wrongEndpoint() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/jokes/wrong")).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

}