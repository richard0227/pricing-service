package com.zara.pricing_service.infrastructure.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void requestAt10_14th() throws Exception {
        performRequest("2020-06-14T10:00:00", 35.50);
    }

    @Test
    void requestAt16_14th() throws Exception {
        performRequest("2020-06-14T16:00:00", 25.45);
    }

    @Test
    void requestAt21_14th() throws Exception {
        performRequest("2020-06-14T21:00:00", 35.50);
    }

    @Test
    void requestAt10_15th() throws Exception {
        performRequest("2020-06-15T10:00:00", 30.50);
    }

    @Test
    void requestAt21_16th() throws Exception {
        performRequest("2020-06-16T21:00:00", 38.95);
    }

    private void performRequest(String date, double expectedPrice) throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", date)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expectedPrice));
    }
}
