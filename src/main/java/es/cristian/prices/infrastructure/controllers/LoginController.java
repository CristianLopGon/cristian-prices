package es.cristian.prices.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cristian.prices.infrastructure.security.AuthResponse;
import es.cristian.prices.infrastructure.security.JwtTokenProvider;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private JwtTokenProvider provider;

	@GetMapping("/token")
	public ResponseEntity<AuthResponse> login() {
		return ResponseEntity.ok(new AuthResponse(provider.generateToken("test")));
	}
}
