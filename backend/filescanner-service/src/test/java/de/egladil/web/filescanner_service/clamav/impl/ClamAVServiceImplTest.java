package de.egladil.web.filescanner_service.clamav.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 */

public class ClamAVServiceImplTest {

	@Test
	void testPing() {

		// Arrange
		ClamAVServiceImpl service = ClamAVServiceImpl.createForLocalTests();

		// Assert
		assertTrue(service.checkAlive());

	}
}
