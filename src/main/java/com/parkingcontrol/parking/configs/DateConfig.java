package com.parkingcontrol.parking.configs;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class DateConfig {
	
	public static final String DATETIME_FORMAT = "yyy-MM-dd'T'HH:mm:ss'z'";
	public static LocalDateTimeSerializer lOCALDateTimeSerializer = new LocalDateTimeSerializer
			(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(lOCALDateTimeSerializer);
		return new ObjectMapper().registerModule(module); 
	}
}
