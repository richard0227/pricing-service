package com.zara.pricing_service.domain.service;

import com.zara.pricing_service.domain.error.PriceNotFoundException;
import com.zara.pricing_service.domain.model.Price;
import com.zara.pricing_service.domain.port.out.PriceLookupPort;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ApplicablePriceSelector {
    private final PriceLookupPort priceLookupPort;

    public ApplicablePriceSelector(PriceLookupPort priceLookupPort) {
        this.priceLookupPort = priceLookupPort;
    }

    public Price selectApplicablePrice(long brandId, long productId, LocalDateTime applicationDate) {
        return priceLookupPort.findBestApplicablePrice(brandId, productId, applicationDate).stream()
                .filter(p -> p.appliesTo(applicationDate))
                .max(Comparator
                        .comparingInt(Price::priority)
                        .thenComparing(Price::startDate)
                        .thenComparingInt(Price::priceList))
                .orElseThrow(() -> new PriceNotFoundException(
                        "No applicable price found for brandId=%d productId=%d at %s"
                                .formatted(brandId, productId, applicationDate)));
    }
}
