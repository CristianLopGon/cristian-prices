package es.cristian.prices.application.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.cristian.prices.application.dto.PriceResponseDTO;
import es.cristian.prices.application.mappers.PriceDTOMapper;
import es.cristian.prices.domain.port.in.PriceService;
import es.cristian.prices.domain.port.out.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {

	private final PriceRepository repository;
	private final PriceDTOMapper mapper;

	public PriceServiceImpl(PriceRepository repository, PriceDTOMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Optional<PriceResponseDTO> getPrice(LocalDateTime date, Long productId, Long brandId) {
		return repository.findApplicablePrice(date, productId, brandId).map(price -> mapper.toDto(price));
	}

}
