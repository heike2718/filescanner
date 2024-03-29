// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * ClientIdFilterTest
 */
@QuarkusTest
public class ClientIdFilterTest {

	private static final String CLIENT_IDS = "Erste,Zweite";

	@Test
	void should_isKnownClientIdreturnTrue_when_present() {

		// Arrange
		String clientId = "Zweite";

		// Act
		boolean result = new ClientIdFilter(CLIENT_IDS).isKnownClientId(clientId);

		// Assert
		assertTrue(result);
	}

	@Test
	void should_isKnownClientIdreturnFalse_when_null() {

		// Arrange
		String clientId = null;

		// Act
		boolean result = new ClientIdFilter(CLIENT_IDS).isKnownClientId(clientId);

		// Assert
		assertFalse(result);
	}

	@Test
	void should_isKnownClientIdreturnFalse_when_presentButLowerCase() {

		// Arrange
		String clientId = "zweite";

		// Act
		boolean result = new ClientIdFilter(CLIENT_IDS).isKnownClientId(clientId);

		// Assert
		assertFalse(result);
	}

	@Test
	void should_isKnownClientIdreturnFalse_when_notPresentAtAll() {

		// Arrange
		String clientId = "Dritte";

		// Act
		boolean result = new ClientIdFilter(CLIENT_IDS).isKnownClientId(clientId);

		// Assert
		assertFalse(result);
	}

	@Test
	void encode() {

		String data = "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*";
		String encoded = Base64.getEncoder().encodeToString(data.getBytes());

		System.out.println(encoded);
	}

}
