// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SecurityIncidentRegistered
 */
public class SecurityIncidentRegistered extends AbstractDomainEvent {

	@JsonProperty
	private String message;

	public SecurityIncidentRegistered withMessage(final String message) {

		this.message = message;
		return this;
	}

	@Override
	@JsonIgnore
	public String typeName() {

		return EventType.SECURITY_INCIDENT_REGISTERED.getLabel();
	}

	@Override
	@JsonIgnore
	public String getMessagingPreview() {

		return message;
	}

}
