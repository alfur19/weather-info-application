package com.example.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testForecast() throws Exception {
        this.mockMvc.perform(get("/forecast"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city", is("Helsinki")))
                .andExpect(jsonPath("forecasts", hasSize(40)))
        ;
    }

    @Test
    public void testForecastWitParameter() throws Exception {
        this.mockMvc.perform(get("/forecast?id="+634964))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city", is("Tampere")))
                .andExpect(jsonPath("forecasts", hasSize(40)))
        ;
    }
}
