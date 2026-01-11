// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.rest;

import jakarta.inject.Singleton;

import io.quarkus.jackson.ObjectMapperCustomizer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public class FilescannerObjectMapperCustomizer implements ObjectMapperCustomizer {

    @Override
    public void customize(final ObjectMapper objectMapper) {

        // To suppress serializing properties with null values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
