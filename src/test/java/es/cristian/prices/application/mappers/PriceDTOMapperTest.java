package es.cristian.prices.application.mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.mockito.Mock;

import es.cristian.prices.application.dto.PriceResponseDTO;

public class PriceDTOMapperTest {

	@Mock
	private PriceDTOMapper mapper;

	LocalDateTime start = LocalDateTime.of(2025, 2, 14, 10, 0);
	LocalDateTime end = LocalDateTime.of(2025, 6, 15, 16, 0);
	PriceResponseDTO dto = new PriceResponseDTO(35455L, 1L, 1, start, end, new BigDecimal(213));

}
