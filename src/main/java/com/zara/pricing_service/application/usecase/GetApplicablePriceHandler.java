package com.zara.pricing_service.application.usecase;

import com.zara.pricing_service.application.dto.ApplicablePriceRequest;
import com.zara.pricing_service.application.dto.ApplicablePriceResponse;
import com.zara.pricing_service.domain.model.Price;
import com.zara.pricing_service.domain.port.in.GetApplicablePriceUseCase;
import com.zara.pricing_service.domain.service.ApplicablePriceSelector;

import java.util.Objects;

public class GetApplicablePriceHandler implements GetApplicablePriceUseCase {

    private final ApplicablePriceSelector applicablePriceSelector;

    public GetApplicablePriceHandler(ApplicablePriceSelector applicablePriceSelector) {
        this.applicablePriceSelector = applicablePriceSelector;
    }

    @Override
    public ApplicablePriceResponse getApplicablePrice(ApplicablePriceRequest request) {
        Objects.requireNonNull(request, "request");
        Objects.requireNonNull(request.applicationDate(), "applicationDate");

        Price selectedPrice = applicablePriceSelector.selectApplicablePrice(
                request.brandId(),
                request.productId(),
                request.applicationDate());

        return ApplicablePriceResponse.from(selectedPrice);
    }
}
