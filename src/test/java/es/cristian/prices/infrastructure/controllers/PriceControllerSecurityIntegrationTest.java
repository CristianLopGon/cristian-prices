package es.cristian.prices.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;

import es.cristian.prices.application.dto.PriceResponseDTO;
import es.cristian.prices.infrastructure.security.JwtTokenProvider;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "test.security.enabled=true")
@ActiveProfiles("test")
public class PriceControllerSecurityIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@LocalServerPort
	private int port;

	private String baseUrl;

	private LocalDateTime date = LocalDateTime.of(2025, 6, 14, 10, 0);
	private Long productId = 35455L;
	private Long brandId = 1L;

	@BeforeEach
	void setUp() {
		baseUrl = "http://localhost:" + port + "/prices";
	}

	@Test
	void search_return200_tokenIsValidTest() throws URISyntaxException {

		String token = jwtTokenProvider.generateToken("test");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Void> request = new HttpEntity(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(new URI(baseUrl)).queryParam("date", date)
				.queryParam("productId", productId).queryParam("brandId", brandId);

		ResponseEntity<PriceResponseDTO> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
				request, PriceResponseDTO.class);

		assertAll(() -> assertEquals(HttpStatus.OK, response.getStatusCode()), () -> assertNotNull(response.getBody()),
				() -> assertEquals(35455L, response.getBody().productId()),
				() -> assertEquals(1L, response.getBody().brandId()),
				() -> assertEquals(4, response.getBody().priceList()),
				() -> assertEquals(new BigDecimal("38.95"), response.getBody().price()));
	}

	@Test
	void search_return401_noTokenTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> request = new HttpEntity(headers);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, request, String.class, date,
				productId, brandId);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	void search_return404_tokenIsValidTest() {
		String token = jwtTokenProvider.generateToken("test");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Void> request = new HttpEntity(headers);

		ResponseEntity<Map> response = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Map.class, date, 7L,
				11111L);

		assertAll(() -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
				() -> assertNotNull(response.getBody()), () -> assertTrue(response.getBody().containsKey("error")),
				() -> assertEquals("Precios no encontrados con estos parámetros.", response.getBody().get("error")));
	}

}
