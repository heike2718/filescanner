// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.scan;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.egladil.web.filescanner_api.domain.clamav.VirusDetection;
import de.egladil.web.filescanner_api.domain.securitychecks.ThreadDetection;

/**
 * ScanResult
 */
@Schema(description = "Ergebnisse des Scans")
public class ScanResult {

	@JsonProperty
	private String userID;

	@JsonProperty
	private String uploadName;

	@JsonProperty
	private String mediaType;

	@JsonProperty
	private VirusDetection virusDetection;

	@JsonProperty
	private ThreadDetection threadDetection;

	public String getMediaType() {

		return mediaType;
	}

	public ScanResult withMediaType(final String mediaType) {

		this.mediaType = mediaType;
		return this;
	}

	public VirusDetection getVirusDetection() {

		return virusDetection;
	}

	public ScanResult withVirusDetection(final VirusDetection virusDetection) {

		this.virusDetection = virusDetection;
		return this;
	}

	public String getUserID() {

		return userID;
	}

	public ScanResult withUserID(final String userID) {

		this.userID = userID;
		return this;
	}

	public String getUploadName() {

		return uploadName;
	}

	public ScanResult withUploadName(final String uploadName) {

		this.uploadName = uploadName;
		return this;
	}

	public ThreadDetection getThreadDetection() {

		return threadDetection;
	}

	public ScanResult withThreadDetection(final ThreadDetection threadDetection) {

		this.threadDetection = threadDetection;
		return this;
	}

}
