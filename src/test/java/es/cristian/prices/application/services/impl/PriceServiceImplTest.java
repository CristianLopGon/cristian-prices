package es.cristian.prices.application.services.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.cristian.prices.application.dto.PriceResponseDTO;
import es.cristian.prices.application.mappers.PriceDTOMapper;
import es.cristian.prices.domain.models.Price;
import es.cristian.prices.domain.port.out.PriceRepository;

@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTest {

	@Mock
	private PriceRepository priceRepository;

	@Mock
	private PriceDTOMapper mapper;

	@InjectMocks
	private PriceServiceImpl priceService;

	LocalDateTime start = LocalDateTime.of(2025, 6, 14, 10, 0);
	LocalDateTime end = LocalDateTime.of(2025, 12, 31, 16, 0);

	Price price = new Price(35455L, 1L, start, end, 1, 1, new BigDecimal(213), "EUR");

	@Test
	void search_returnValidTest() {
		when(priceRepository.findApplicablePrice(LocalDateTime.of(2025, 8, 31, 16, 0), 35455L, 1L))
				.thenReturn(Optional.of(price));

		Optional<PriceResponseDTO> responseDto = priceService.getPrice(LocalDateTime.of(2025, 8, 31, 16, 0), 35455L,
				1L);

		assertAll(() -> assertTrue(responseDto.isPresent()), () -> assertEquals(35455L, responseDto.get().productId()),
				() -> assertEquals(1L, responseDto.get().brandId()));
	}

}
