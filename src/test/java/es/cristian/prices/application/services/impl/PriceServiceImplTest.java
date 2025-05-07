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

	LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
	LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

	Price price = new Price(35455L, 1L, start, end, 1, 1, new BigDecimal(35.50), "EUR");

	@Test
	void search_returnValidTest() {
		when(priceRepository.findApplicablePrice(LocalDateTime.of(2020, 8, 31, 16, 0), 35455L, 1L))
				.thenReturn(Optional.of(price));
		when(mapper.toDto(price)).thenReturn(new PriceResponseDTO(35455L, 1L, 1, start, end, new BigDecimal(35.50)));

		Optional<PriceResponseDTO> responseDto = priceService.getPrice(LocalDateTime.of(2020, 8, 31, 16, 0), 35455L,
				1L);

		assertAll(() -> assertTrue(responseDto.isPresent()), () -> assertEquals(35455L, responseDto.get().productId()),
				() -> assertEquals(1L, responseDto.get().brandId()),
				() -> assertEquals(1, responseDto.get().priceList()));
	}

}
