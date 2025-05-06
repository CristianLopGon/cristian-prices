package es.cristian.prices.infrastructure.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MultiFormatDateConverter implements Converter<String, LocalDateTime> {

	private final List<DateTimeFormatter> formatters = List.of(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"),
			DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
			DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

	@Override
	public LocalDateTime convert(String source) {
		for (DateTimeFormatter formatter : formatters) {
			try {
				return LocalDateTime.parse(source, formatter);
			} catch (DateTimeParseException ignored) {
			}
		}
		throw new IllegalArgumentException("Formato de fecha no válido: " + source);
	}
}