// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.securitychecks;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ThreadDetection
 */
public class ThreadDetection {

	@JsonProperty
	private final boolean securityThreadDetected;

	@JsonProperty
	private String securityCheckMessage;

	public static final ThreadDetection TRUE = new ThreadDetection(true);

	public static final ThreadDetection FALSE = new ThreadDetection(false);

	private ThreadDetection(final boolean securityThreadDetected) {

		this.securityThreadDetected = securityThreadDetected;
	}

	public String getSecurityCheckMessage() {

		return securityCheckMessage;
	}

	public ThreadDetection withSecurityCheckMessage(final String securityCheckMessage) {

		this.securityCheckMessage = securityCheckMessage;
		return this;
	}

	public boolean isSecurityThreadDetected() {

		return securityThreadDetected;
	}
}
