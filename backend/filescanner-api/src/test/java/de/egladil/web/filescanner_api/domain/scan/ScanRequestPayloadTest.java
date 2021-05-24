// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.scan;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ScanRequestPayloadTest
 */
public class ScanRequestPayloadTest {

	@Test
	void should_serialize() throws JsonProcessingException {

		// Arrange
		Upload upload = new Upload().withName("zipbomb-decompress-at-your-own-risk.txt")
			.withData(loadImageDataQuetly("/zipbomb-decompress-at-your-own-risk.txt"));
		ScanRequestPayload payload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		String serialization = new ObjectMapper().writeValueAsString(payload);

		System.out.println(serialization);

	}

	private byte[] loadImageDataQuetly(final String classpathLocation) {

		try (InputStream in = getClass().getResourceAsStream(classpathLocation)) {

			return in.readAllBytes();
		} catch (IOException e) {

			throw new RuntimeException("Test nicht möglich: " + e.getMessage(), e);
		}

	}

}
