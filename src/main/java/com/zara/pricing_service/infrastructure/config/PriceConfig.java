package com.zara.pricing_service.infrastructure.config;

import com.zara.pricing_service.application.dto.ApplicablePriceRequest;
import com.zara.pricing_service.application.dto.ApplicablePriceResponse;
import com.zara.pricing_service.application.usecase.GetApplicablePriceHandler;
import com.zara.pricing_service.domain.port.in.GetApplicablePriceUseCase;
import com.zara.pricing_service.domain.port.out.PriceLookupPort;
import com.zara.pricing_service.domain.service.ApplicablePriceSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceConfig {

    @Bean
    ApplicablePriceSelector priceFinder(PriceLookupPort port) {
        return new ApplicablePriceSelector(port);
    }

    @Bean
    GetApplicablePriceUseCase getApplicablePriceUseCase(ApplicablePriceSelector selector) {
        return new GetApplicablePriceHandler(selector);
    }
}
