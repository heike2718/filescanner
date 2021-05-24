// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.event;

/**
 * EventType
 */
public enum EventType {

	SECURITY_INCIDENT_REGISTERED("SecurityIncidentRegistered"),
	VIRUS_DETECTD("VirusDetected"),
	ZIP_BOMB_DETECTED("ZipBombDetected");

	private final String label;

	private EventType(final String label) {

		this.label = label;
	}

	public String getLabel() {

		return label;
	}

}
