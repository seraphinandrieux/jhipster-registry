package io.github.jhipster.registry.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.registry.web.rest.vm.LoggerVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LogsResourceTest {

    private MockMvc mock;

    @BeforeEach
    public void setup() {
        this.mock = MockMvcBuilders.standaloneSetup(new LogsResource()).build();
    }

    @Test
    public void getListTest() throws Exception {
        mock.perform(get("/management/logs")
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void changeLevelTest() throws Exception {
        LoggerVM logger = new LoggerVM();
        logger.setLevel("ERROR");
        logger.setName("ROOT");

        mock.perform(put("/management/logs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(logger)))
            .andExpect(status().isNoContent());

        MvcResult res = mock.perform(get("/management/logs")
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn();

        assertThat(res.getResponse().getContentAsString()).contains("\"name\":\""+logger.getName()
            +"\",\"level\":\""+logger.getLevel()+"\"");
    }

}
