// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.egladil.web.commons_net.time.CommonTimeUtils;

/**
 * AbstractFilescannerDomainEvent
 */
public abstract class AbstractFilescannerDomainEvent implements FilescannerDomainEvent {

	@JsonIgnore
	private final LocalDateTime occuredOn;

	protected AbstractFilescannerDomainEvent() {

		this.occuredOn = CommonTimeUtils.now();
	}

	@Override
	public LocalDateTime occuredOn() {

		return occuredOn;
	}

	@Override
	public String serializeQuietly() {

		try {

			String body = new ObjectMapper().writeValueAsString(this);
			return typeName() + ": " + body;
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return e.getMessage();
		}
	}
}
