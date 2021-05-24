// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FilescannerDomainEvent
 */
public interface FilescannerDomainEvent {

	/**
	 * @return LocalDateTime
	 */
	@JsonIgnore
	LocalDateTime occuredOn();

	/**
	 * @return String
	 */
	@JsonProperty
	String typeName();

	/**
	 * Gibt einen Text aus, der in einem Messager oder als Mailtext versendet werden kann.
	 *
	 * @return
	 */
	@JsonIgnore
	String getMessagingPreview();

	@JsonIgnore
	String serializeQuietly();
}
