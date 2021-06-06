// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.scan;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.egladil.web.filescanner_api.domain.TestFileUtils;

/**
 * ScanRequestPayloadTest
 */
public class ScanRequestPayloadTest {

	@Test
	void should_serialize() throws JsonProcessingException {

		// Arrange
		Upload upload = new Upload().withName("zipbomb-decompress-at-your-own-risk.txt")
			.withData(TestFileUtils.loadDataQuietly("/zipbomb-decompress-at-your-own-risk.txt"));
		ScanRequestPayload payload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		String serialization = new ObjectMapper().writeValueAsString(payload);

		System.out.println(serialization);

	}

}
