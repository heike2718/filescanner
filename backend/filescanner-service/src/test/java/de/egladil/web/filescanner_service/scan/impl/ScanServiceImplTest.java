// =====================================================
// Project: filescanner-service
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.scan.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.egladil.web.filescanner_service.TestFileUtils;
import de.egladil.web.filescanner_service.scan.ScanRequestPayload;
import de.egladil.web.filescanner_service.scan.ScanResult;
import de.egladil.web.filescanner_service.scan.Upload;
import de.egladil.web.filescanner_service.securitychecks.ThreadDetection;

/**
 * ScanServiceImplTest
 */
public class ScanServiceImplTest {

	ScanServiceImpl scanService;

	@BeforeEach
	void setUp() {

		this.scanService = ScanServiceImpl.createForIntegrationTest();

	}

	@Test
	void should_scanWorkWithLargeFile() {

		// Arrange
		Upload upload = new Upload().withName("Sample1point5MB.xls")
			.withData(TestFileUtils.loadDataQuietly("/Sample1point5MB.xls"));
		ScanRequestPayload payload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		ScanResult result = scanService.scanFile(payload);

		// Assert
		assertFalse(result.getVirusDetection().isVirusDetected());
		assertFalse(result.getThreadDetection().isSecurityThreadDetected());
		assertEquals("application/vnd.ms-excel", result.getMediaType());
	}

	@Test
	void should_scanDetectZipBomb() {

		// Arrange
		Upload upload = new Upload().withName("zipbomb-decompress-at-your-own-risk.txt")
			.withData(TestFileUtils.loadDataQuietly("/zipbomb-decompress-at-your-own-risk.txt"));
		ScanRequestPayload payload = new ScanRequestPayload().withClientId("k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J")
			.withFileOwner("heike").withUpload(upload);

		// Act
		ScanResult result = scanService.scanFile(payload);

		// Assert
		assertFalse(result.getVirusDetection().isVirusDetected());
		ThreadDetection threadDetection = result.getThreadDetection();
		assertTrue(threadDetection.isSecurityThreadDetected());
		assertEquals("Verdacht auf ZIP-Bombe", threadDetection.getSecurityCheckMessage());
	}

}
