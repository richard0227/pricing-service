package com.zara.pricing_service.domain.port.in;

import com.zara.pricing_service.application.dto.ApplicablePriceRequest;
import com.zara.pricing_service.application.dto.ApplicablePriceResponse;

public interface GetApplicablePriceUseCase {
    ApplicablePriceResponse getApplicablePrice(ApplicablePriceRequest request);
}
