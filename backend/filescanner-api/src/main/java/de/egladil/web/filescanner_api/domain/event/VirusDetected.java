// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.event;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VirusDetected
 */
public class VirusDetected extends AbstractDomainEvent {

	@JsonProperty
	private String clientId;

	@JsonProperty
	private String ownerId;

	@JsonProperty
	private String fileName;

	@JsonProperty
	private String virusScannerMessage;

	@Override
	public String toString() {

		return "VirusDetected [clientId=" + clientId + ", ownerId=" + ownerId + ", fileName=" + fileName + ", virusScannerMessage="
			+ virusScannerMessage + "]";
	}

	@Override
	@JsonIgnore
	public String typeName() {

		return EventType.VIRUS_DETECTD.getLabel();
	}

	public String getOwnerId() {

		return ownerId;
	}

	public VirusDetected withOwnerId(final String ownerId) {

		this.ownerId = ownerId;
		return this;
	}

	public String getVirusScannerMessage() {

		return virusScannerMessage;
	}

	public VirusDetected withVirusScannerMessage(final String virusScannerMessage) {

		this.virusScannerMessage = virusScannerMessage;
		return this;
	}

	public String getFileName() {

		return fileName;
	}

	public VirusDetected withFileName(final String fileName) {

		this.fileName = fileName;
		return this;
	}

	@Override
	@JsonIgnore
	public String getMessagingPreview() {

		return "VirusDetected [clientId=" + StringUtils.abbreviate(clientId, 11) + ", ownerId="
			+ StringUtils.abbreviate(ownerId, 11) + ", fileName=" + fileName + ", virusScannerMessage="
			+ virusScannerMessage + "]";
	}

	public String getClientId() {

		return clientId;
	}

	public VirusDetected withClientId(final String clientId) {

		this.clientId = StringUtils.abbreviate(clientId, 11);
		return this;
	}
}
