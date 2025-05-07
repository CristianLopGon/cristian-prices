package es.cristian.prices.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Servicio de búsquedas", version = "1.0", description = "Consulta de precios y tarifas"), security = @SecurityRequirement(name = "bearerAuth"))
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.info(apiInfo());
	}

	private io.swagger.v3.oas.models.info.Info apiInfo() {

		return new io.swagger.v3.oas.models.info.Info().title("Api de búsquedas")
				.description("API para buscar precios y tarifa aplicada al producto").version("1.0").contact(contact());

	}

	private Contact contact() {
		Contact contact = new Contact();
		contact.setEmail("cristianlopgon@gmail.com");
		contact.setName("Cristian López González");
		return contact;
	}

}
