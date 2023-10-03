// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;

/**
 * MockMailerProfile
 */
public class MockMailerProfile implements QuarkusTestProfile {

	@Override
	public String getConfigProfile() {

		// aus application.properties
		return "mock-mailbox-test";
	}

}
