package es.cristian.prices.infrastructure.repositories.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import es.cristian.prices.domain.models.Price;
import es.cristian.prices.domain.port.out.PriceRepository;
import es.cristian.prices.infrastructure.mappers.PriceEntityMapper;
import es.cristian.prices.infrastructure.repositories.PriceJPARepository;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

	private final PriceJPARepository jpaRepository;
	private final PriceEntityMapper mapper;

	public PriceRepositoryImpl(PriceJPARepository jpaRepository, PriceEntityMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Optional<Price> findApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
		return jpaRepository.findApplicablePrices(date, productId, brandId).stream().findFirst()
				.map(entity -> mapper.toDomain(entity));
	}

}
