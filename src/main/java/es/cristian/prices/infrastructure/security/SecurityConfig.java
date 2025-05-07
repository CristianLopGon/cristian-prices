package es.cristian.prices.infrastructure.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

	private final JwtTokenProvider provider;
	private final SecurityProperties properties;

	public SecurityConfig(JwtTokenProvider provider, SecurityProperties properties) {
		this.provider = provider;
		this.properties = properties;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		if (!properties.isEnabled()) {
			http.csrf((csrf) -> csrf.disable())
					.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.anyRequest().permitAll());
			return http.build();
		}

		http.csrf((csrf) -> csrf.disable())
				.sessionManagement(
						(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/login/**").permitAll().anyRequest()
						.authenticated())
				.addFilterBefore(new JwtAuthenticationFilter(provider), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling((ex) -> ex.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
						.accessDeniedHandler((request, response, accessDeniedException) -> response
								.sendError(HttpServletResponse.SC_FORBIDDEN)));
		return http.build();

	}

}
