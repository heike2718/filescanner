// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.event;

/**
 * Inter
 */
public interface DomainEventService {

	/**
	 * Tut, was mit einem FilescannerDomainEvent event getan werden muss.
	 *
	 * @param event
	 */
	void handleDomainEvent(FilescannerDomainEvent event);

}
