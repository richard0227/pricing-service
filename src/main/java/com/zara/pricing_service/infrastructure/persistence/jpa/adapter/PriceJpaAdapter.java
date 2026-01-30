package com.zara.pricing_service.infrastructure.persistence.jpa.adapter;

import com.zara.pricing_service.domain.model.Price;
import com.zara.pricing_service.domain.port.out.PriceLookupPort;
import com.zara.pricing_service.infrastructure.persistence.jpa.mapper.PriceEntityMapper;
import com.zara.pricing_service.infrastructure.persistence.jpa.repository.PriceJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceJpaAdapter implements PriceLookupPort {

    private final PriceJpaRepository repository;
    private final PriceEntityMapper mapper = new PriceEntityMapper();

    public PriceJpaAdapter(PriceJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Price> findBestApplicablePrice(long brandId, long productId, LocalDateTime applicationDate) {
        return repository.findBestApplicablePrice(brandId, productId, applicationDate).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
