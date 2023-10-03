// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.junit.jupiter.api.Test;

import de.egladil.web.filescanner_api.TestFileUtils;
import de.egladil.web.filescanner_api.domain.MessagePayload;
import de.egladil.web.filescanner_api.domain.clamav.VirusDetection;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.ScanResult;
import de.egladil.web.filescanner_api.domain.scan.Upload;
import de.egladil.web.filescanner_api.domain.securitychecks.ThreadDetection;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

/**
 * FilescannerResourceTest
 */
@QuarkusTest
@TestHTTPEndpoint(FilescannerResource.class)
public class FilescannerResourceTest {

	/**
	 *
	 */
	private static final String VALID_CLIENT_ID = "NBptB82KjFkelkF55Aq4SmQSL3DXZHHurbe7l5W9LT7U";

	@Test
	void testTheHelloEndpoint() {

		MessagePayload responsePayload = given().when()
			.get("hello")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(MessagePayload.class);

		// Assert
		assertTrue(responsePayload.isOk());
		assertEquals(
			"Hallo hier spricht die FilescannerResource der filescanner-api vom Port 9800. Um eine Datei zu scannen, URL /files/detection als POST - request mit einem ScanRequestPayload aufrufen.",
			responsePayload.getMessage());

	}

	@Test
	void testThePingEndpointWithCorrectClientId() {

		given().when()
			.header("Accept", "application/json")
			.header("X-CLIENT-ID", VALID_CLIENT_ID)
			.get("ping/v1")
			.then()
			.statusCode(200);
	}

	@Test
	void testThePingEndpointWithUnknownClientId() {

		MessagePayload mp = given().when()
			.header("Accept", "application/json")
			.header("X-CLIENT-ID", "hallo")
			.get("ping/v1")
			.then()
			.statusCode(401)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(MessagePayload.class);

		assertEquals("ERROR", mp.getLevel());
		assertEquals("keine Berechtigung", mp.getMessage());
	}

	@Test
	void testThePingEndpointWithClientIdBlank() {

		given().when()
			.header("Accept", "application/json")
			.header("X-CLIENT-ID", "")
			.get("ping/v1")
			.then()
			.statusCode(401);
	}

	@Test
	void testThePingEndpointWithoutClient() {

		given().when()
			.header("Accept", "application/json")
			.get("ping/v1")
			.then()
			.statusCode(400);
	}

	@Test
	void testDetectionEndpointNoThread() {

		Upload upload = new Upload().withName("normal.txt")
			.withData("hallo auch".getBytes());

		ScanRequestPayload requestPayload = new ScanRequestPayload()
			.withClientId(VALID_CLIENT_ID)
			.withFileOwner("ichedoche").withUpload(upload);

		ScanResult scanResult = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(ScanResult.class);

		// Assert
		VirusDetection detection = scanResult.getVirusDetection();
		assertFalse(detection.isVirusDetected());
	}

	@Test
	void should_scanWorkWithLargeFile() {

		// Arrange
		Upload upload = new Upload().withName("Sample1point5MB.xlsx")
			.withData(TestFileUtils.loadDataQuietly("/Sample1point5MB.xlsx"));
		ScanRequestPayload requestPayload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		ScanResult scanResult = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(ScanResult.class);

		// Assert
		VirusDetection detection = scanResult.getVirusDetection();
		assertFalse(detection.isVirusDetected());

		ThreadDetection threadDetection = scanResult.getThreadDetection();
		assertFalse(threadDetection.isSecurityThreadDetected());

		assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", scanResult.getMediaType());

	}

	@Test
	void testMeduaTypeAltesExcel() {

		// Arrange
		Upload upload = new Upload().withName("excel_altes_format.xls")
			.withData(TestFileUtils.loadDataQuietly("/excel_altes_format.xls"));
		ScanRequestPayload requestPayload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		ScanResult scanResult = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(ScanResult.class);

		// Assert
		VirusDetection detection = scanResult.getVirusDetection();
		assertFalse(detection.isVirusDetected());

		ThreadDetection threadDetection = scanResult.getThreadDetection();
		assertFalse(threadDetection.isSecurityThreadDetected());

		assertEquals("application/vnd.ms-excel", scanResult.getMediaType());
	}

	@Test
	void testMeduaTypeEps() {

		// Arrange
		Upload upload = new Upload().withName("01149.eps")
			.withData(TestFileUtils.loadDataQuietly("/01149.eps"));
		ScanRequestPayload requestPayload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		ScanResult scanResult = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(ScanResult.class);

		// Assert
		VirusDetection detection = scanResult.getVirusDetection();
		assertFalse(detection.isVirusDetected());

		ThreadDetection threadDetection = scanResult.getThreadDetection();
		assertFalse(threadDetection.isSecurityThreadDetected());

		assertEquals("application/postscript", scanResult.getMediaType());
	}

	@Test
	void testMeduaTypeJpg() {

		// Arrange
		Upload upload = new Upload().withName("avatar.jpg")
			.withData(TestFileUtils.loadDataQuietly("/avatar.jpg"));
		ScanRequestPayload requestPayload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		ScanResult scanResult = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(ScanResult.class);

		// Assert
		VirusDetection detection = scanResult.getVirusDetection();
		assertFalse(detection.isVirusDetected());

		ThreadDetection threadDetection = scanResult.getThreadDetection();
		assertFalse(threadDetection.isSecurityThreadDetected());

		assertEquals("image/jpeg", scanResult.getMediaType());
	}

	@Test
	void testMeduaTypeOds() {

		// Arrange
		Upload upload = new Upload().withName("test.ods")
			.withData(TestFileUtils.loadDataQuietly("/test.ods"));
		ScanRequestPayload requestPayload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		ScanResult scanResult = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(ScanResult.class);

		// Assert
		VirusDetection detection = scanResult.getVirusDetection();
		assertFalse(detection.isVirusDetected());

		ThreadDetection threadDetection = scanResult.getThreadDetection();
		assertFalse(threadDetection.isSecurityThreadDetected());

		assertEquals("application/vnd.oasis.opendocument.spreadsheet", scanResult.getMediaType());
	}

	@Test
	void testDetectionEndpointWithVirus() {

		// Für den RestAssured-Test muss man das decoden, weil Quarkus es encoded.
		byte[] content = Base64.getDecoder()
			.decode("WDVPIVAlQEFQWzRcUFpYNTQoUF4pN0NDKTd9JEVJQ0FSLVNUQU5EQVJELUFOVElWSVJVUy1URVNULUZJTEUhJEgrSCo=".getBytes());

		Upload upload = new Upload().withName("uebel.txt")
			.withData(content);

		ScanRequestPayload requestPayload = new ScanRequestPayload()
			.withClientId(VALID_CLIENT_ID)
			.withFileOwner("ichedoche").withUpload(upload);

		ScanResult scanResult = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(ScanResult.class);

		// Assert
		VirusDetection detection = scanResult.getVirusDetection();
		assertTrue(detection.isVirusDetected());
		assertEquals("stream: Eicar-Test-Signature FOUND", detection.getScannerMessage());
	}

	@Test
	void testDetectionWithZipBomb() {

		Upload upload = new Upload().withName("zipbomb-decompress-at-your-own-risk.txt")
			.withData(TestFileUtils.loadDataQuietly("/zipbomb-decompress-at-your-own-risk.txt"));
		ScanRequestPayload requestPayload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		ScanResult scanResult = given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract()
			.as(ScanResult.class);

		// Assert
		VirusDetection detection = scanResult.getVirusDetection();
		assertFalse(detection.isVirusDetected());

		ThreadDetection threadDetection = scanResult.getThreadDetection();
		assertTrue(threadDetection.isSecurityThreadDetected());
		assertEquals("Verdacht auf ZIP-Bombe", threadDetection.getSecurityCheckMessage());
	}

	@Test
	void should_scanReturn401_when_invalidClientID() {

		ScanRequestPayload requestPayload = new ScanRequestPayload()
			.withClientId("hallo")
			.withFileOwner("ichedoche").withUpload(new Upload().withName("grusz.txt").withData("ja, hallo auch".getBytes()));

		given().when()
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.body(requestPayload)
			.post("detection/v1")
			.then()
			.statusCode(401);

	}
}
