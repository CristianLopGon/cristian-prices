package es.cristian.prices.domain.port.in;

import java.time.LocalDateTime;
import java.util.Optional;

import es.cristian.prices.application.dto.PriceResponseDTO;

public interface PriceService {

	Optional<PriceResponseDTO> getPrice(LocalDateTime date, Long productId, Long brandId);
}
