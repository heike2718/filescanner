// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.clamav;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VirusDetection
 */
public class VirusDetection {

	@JsonProperty
	private final boolean virusDetected;

	@JsonProperty
	private String scannerMessage;

	public static final VirusDetection TRUE = new VirusDetection(true);

	public static final VirusDetection FALSE = new VirusDetection(false);

	private VirusDetection() {

		virusDetected = false;
	}

	/**
	 * @param virusDetected
	 */
	private VirusDetection(final boolean virusDetected) {

		this.virusDetected = virusDetected;
	}

	@Override
	public String toString() {

		return "VirusDetection [virusDetected=" + virusDetected + ", scannerMessage=" + scannerMessage + "]";
	}

	public boolean isVirusDetected() {

		return virusDetected;
	}

	public String getScannerMessage() {

		return scannerMessage;
	}

	public VirusDetection withScannerMessage(final String scannerMessage) {

		this.scannerMessage = scannerMessage;
		return this;
	}
}
