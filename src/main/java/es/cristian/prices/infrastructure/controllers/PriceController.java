package es.cristian.prices.infrastructure.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cristian.prices.application.dto.PriceRequestDTO;
import es.cristian.prices.application.dto.PriceResponseDTO;
import es.cristian.prices.application.exceptions.SearchNotFoundException;
import es.cristian.prices.domain.port.in.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/prices")
public class PriceController {

	private final Logger logger = LoggerFactory.getLogger(PriceController.class);
	private final PriceService service;

	public PriceController(PriceService service) {
		this.service = service;
	}

	@Operation(summary = "Obtiene el precio y tarifa a aplicar a un producto en cierto intervalo de fecha")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = "application/json") }, description = ""),
			@ApiResponse(responseCode = "404", description = "Precios no encontrados con estos parámetros.") })
	@GetMapping
	public ResponseEntity<PriceResponseDTO> getPrice(@ModelAttribute @Valid PriceRequestDTO priceDto) {
		logger.info("Entrada de PriceController: {}, {}, {}", priceDto.date().toString(), priceDto.productId(),
				priceDto.brandId());
		return service.getPrice(priceDto.date(), priceDto.productId(), priceDto.brandId()).map(ResponseEntity::ok)
				.orElseThrow(() -> new SearchNotFoundException("Precios no encontrados con estos parámetros."));
	}
}
