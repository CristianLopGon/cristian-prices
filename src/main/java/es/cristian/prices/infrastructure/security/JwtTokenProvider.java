package es.cristian.prices.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final String secretKey = "claveEstaticaSuperSecretaDeEjemplo";
	private final long validTime = 1000 * 60 * 10; // 10 minutos

	public String generateToken(String username) {

		Date now = new Date();
		Date validTokenTime = new Date(now.getTime() + validTime);
		return Jwts.builder().subject(username).issuedAt(now).expiration(validTokenTime)
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))).compact();
	}

	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		String username = getClaims(token).getSubject();
		return new UsernamePasswordAuthenticationToken(username, "", List.of());
	}

	private Claims getClaims(String token) {
		return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))).build()
				.parseSignedClaims(token).getPayload();
	}
}
