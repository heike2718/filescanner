// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import de.egladil.web.filescanner_api.domain.MessagePayload;
import de.egladil.web.filescanner_api.domain.clamav.ClamAVService;
import de.egladil.web.filescanner_api.domain.error.FilescannerRuntimeException;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.ScanService;
import de.egladil.web.filescanner_api.domain.scan.Upload;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

/**
 * FilescannerResourceServerErrorTest
 */
@QuarkusTest
@TestHTTPEndpoint(FilescannerResource.class)
public class FilescannerResourceServerErrorTest {

	private static final String VALID_CLIENT_ID = "NBptB82KjFkelkF55Aq4SmQSL3DXZHHurbe7l5W9LT7U";

	@InjectMock
	ScanService scanService;

	@InjectMock
	ClamAVService clamAVService;

	@Test
	void should_handleServerError() {

		when(scanService.scanFile(any(ScanRequestPayload.class))).thenThrow(new FilescannerRuntimeException("ui, das ging schief"));

		Upload upload = new Upload().withName("normal.txt")
			.withData("hallo auch".getBytes());

		ScanRequestPayload requestPayload = new ScanRequestPayload()
			.withClientId("NBptB82KjFkelkF55Aq4SmQSL3DXZHHurbe7l5W9LT7U")
			.withFileOwner("ichedoche").withUpload(upload);

		MessagePayload responsePayload = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(500)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(MessagePayload.class);

		// Assert
		assertEquals("ERROR", responsePayload.getLevel());
		assertEquals("internalServerError", responsePayload.getMessage());
	}

	@Test
	void testThePingEndpointWithCorrectClientId_when_ClamAVNichtErreichbar() {

		when(clamAVService.checkAlive()).thenReturn(Boolean.FALSE);

		given().when()
			.header("Accept", "application/json")
			.header("X-CLIENT-ID", VALID_CLIENT_ID)
			.get("ping/v1")
			.then()
			.statusCode(503);
	}

}
