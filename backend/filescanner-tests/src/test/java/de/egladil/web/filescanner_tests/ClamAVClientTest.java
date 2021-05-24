// =====================================================
// Project: filescanner-tests
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import fi.solita.clamav.ClamAVClient;

/**
 * ClamAVClientTest
 */
public class ClamAVClientTest {

	private static final String HOST = "192.168.10.176";

	private static final int PORT = 3310;

	private static final int TIMEOUT = 5000;

	@Test
	void scanCleanFile() throws IOException {

		// Arrange
		ClamAVClient clamAVClient = new ClamAVClient(HOST, PORT, TIMEOUT);

		try (InputStream in = getClass().getResourceAsStream("/test.txt")) {

			// Act
			byte[] result = clamAVClient.scan(in);

			// Assert
			assertTrue(new String(result).contains("OK"));
		}

	}

	@Test
	void scanLargeFile() throws IOException {

		// Arrange
		ClamAVClient clamAVClient = new ClamAVClient(HOST, PORT, TIMEOUT);

		for (int i = 0; i < 20; i++) {

			long startTime = System.currentTimeMillis();

			try (InputStream in = getClass().getResourceAsStream("/large-file.txt")) {

				// Act
				byte[] result = clamAVClient.scan(in);

				long duration = System.currentTimeMillis() - startTime;

				System.out.println("duration = " + duration + " ms");
				// Assert
				assertTrue(new String(result).contains("OK"));
			}
		}
	}

	@Test
	void scanFileWithVirus() throws IOException {

		// Arrange
		ClamAVClient clamAVClient = new ClamAVClient(HOST, PORT, TIMEOUT);

		try (InputStream in = getClass().getResourceAsStream("/eicar.com.txt")) {

			// Act
			byte[] result = clamAVClient.scan(in);

			// Assert
			String resultAsString = new String(result);
			assertTrue(resultAsString.contains("Win.Test.EICAR_HDB-1 FOUND"));
		}
	}
}
