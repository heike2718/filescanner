// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FilescannerSecurityIncidentRegistered
 */
public class FilescannerSecurityIncidentRegistered extends AbstractFilescannerDomainEvent {

	@JsonProperty
	private String message;

	public FilescannerSecurityIncidentRegistered withMessage(final String message) {

		this.message = message;
		return this;
	}

	@Override
	@JsonIgnore
	public String typeName() {

		return FilescannerEventType.SECURITY_INCIDENT_REGISTERED.getLabel();
	}

	@Override
	@JsonIgnore
	public String getMessagingPreview() {

		return message;
	}

}
