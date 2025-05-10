package com.vois.iot.warehousing.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.HttpHeaders.ORIGIN;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationCorsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowCorsForFrontendOrigin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.options("/api/devices")
                        .header(ORIGIN, "http://localhost:3000")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Origin", "http://localhost:3000"))
                .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Credentials", "true"))
                .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"));
    }
}
