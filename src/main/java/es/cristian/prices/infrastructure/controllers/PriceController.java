package es.cristian.prices.infrastructure.controllers;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.cristian.prices.application.dto.PriceResponseDTO;
import es.cristian.prices.application.exceptions.SearchNotFoundException;
import es.cristian.prices.domain.port.in.PriceService;

@RestController
@RequestMapping("/prices")
public class PriceController {

	private final PriceService service;

	public PriceController(PriceService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<PriceResponseDTO> getPrice(@RequestParam LocalDateTime date, @RequestParam Long productId,
			@RequestParam Long brandId) {
		return service.getPrice(date, productId, brandId).map(ResponseEntity::ok)
				.orElseThrow(() -> new SearchNotFoundException("Precios no encontrados con estos parámetros."));
	}
}
