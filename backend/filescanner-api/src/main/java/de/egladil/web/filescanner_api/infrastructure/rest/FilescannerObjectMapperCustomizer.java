// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.rest;

import javax.inject.Singleton;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
public class FilescannerObjectMapperCustomizer implements ObjectMapperCustomizer {

	@Override
	public void customize(final ObjectMapper objectMapper) {

		// To suppress serializing properties with null values
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
}
