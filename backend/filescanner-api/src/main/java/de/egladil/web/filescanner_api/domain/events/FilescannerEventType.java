// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.events;

/**
 * FilescannerEventType
 */
public enum FilescannerEventType {

	SECURITY_INCIDENT_REGISTERED("FilescannerSecurityIncidentRegistered"),
	VIRUS_DETECTD("VirusDetected"),
	ZIP_BOMB_DETECTED("ZipBombDetected");

	private final String label;

	private FilescannerEventType(final String label) {

		this.label = label;
	}

	public String getLabel() {

		return label;
	}

}
