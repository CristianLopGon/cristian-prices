package es.cristian.prices.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import es.cristian.prices.application.dto.PriceResponseDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "test.security.enabled=false")
@ActiveProfiles("test")
public class PriceControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String baseUrl;

	private String date = "2025-06-18T10:00:00";
	private Long productId = 35455L;
	private Long brandId = 1L;

	@BeforeEach
	void setUp() {
		baseUrl = "http://localhost:" + port + "/prices";
	}

	@Test
	void search_return200Test() {
		ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
				baseUrl + "?date={date}&productId={productId}&brandId={brandId}", PriceResponseDTO.class, date,
				productId, brandId);

		assertAll(() -> assertEquals(HttpStatus.OK, response.getStatusCode()), () -> assertNotNull(response.getBody()),
				() -> assertEquals(35455L, response.getBody().productId()),
				() -> assertEquals(1L, response.getBody().brandId()),
				() -> assertEquals(4, response.getBody().priceList()),
				() -> assertEquals(new BigDecimal("38.95"), response.getBody().price()));
	}

	@Test
	void search_return404Test() {
		ResponseEntity<Map> response = restTemplate.getForEntity(
				baseUrl + "?date={date}&productId={productId}&brandId={brandId}", Map.class, date, 11111L, 7L);

		assertAll(() -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
				() -> assertNotNull(response.getBody()), () -> assertTrue(response.getBody().containsKey("error")),
				() -> assertEquals("Precios no encontrados con estos parámetros.", response.getBody().get("error")));
	}

}
