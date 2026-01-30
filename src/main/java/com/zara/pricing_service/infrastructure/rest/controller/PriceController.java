package com.zara.pricing_service.infrastructure.rest.controller;

import com.zara.pricing_service.application.dto.ApplicablePriceRequest;
import com.zara.pricing_service.application.dto.ApplicablePriceResponse;
import com.zara.pricing_service.domain.port.in.GetApplicablePriceUseCase;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {

    private final GetApplicablePriceUseCase useCase;

    public PriceController(GetApplicablePriceUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ApplicablePriceResponse getPrice(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam @Min(1) long productId,
            @RequestParam @Min(1) long brandId
    ) {
        return useCase.getApplicablePrice(new ApplicablePriceRequest(brandId, productId, applicationDate));
    }
}
