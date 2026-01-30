package com.zara.pricing_service.infrastructure.rest.advice;

import com.zara.pricing_service.domain.error.PriceNotFoundException;
import com.zara.pricing_service.domain.port.in.GetApplicablePriceUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestExceptionHandlerTest {

    @Autowired
    MockMvc mvc;

    @Mock
    GetApplicablePriceUseCase useCase;

    @Test
    void returns_404_problem_detail_when_price_not_found() throws Exception {
        given(useCase.getApplicablePrice(org.mockito.ArgumentMatchers.any()))
                .willThrow(new PriceNotFoundException("not found"));

        mvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "2"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.title").value("Price not found"))
                .andExpect(jsonPath("$.detail").value("No applicable price found for brandId=2 productId=35455 at 2020-06-14T10:00"));
    }

    @Test
    void returns_400_problem_detail_when_request_is_invalid() throws Exception {
        mvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "INVALID_DATE")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.title").value("Bad request"));
    }
}
