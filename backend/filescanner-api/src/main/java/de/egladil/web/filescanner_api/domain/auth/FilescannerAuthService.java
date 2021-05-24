// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.egladil.web.filescanner_api.domain.event.DomainEventService;
import de.egladil.web.filescanner_api.domain.event.SecurityIncidentRegistered;

/**
 * FilescannerAuthService
 */
@ApplicationScoped
public class FilescannerAuthService {

	@ConfigProperty(name = "clientIds")
	String clientIds;

	@Inject
	DomainEventService eventService;

	public boolean isKnown(final String clientId) {

		boolean present = new ClientIdFilter(clientIds).isKnownClientId(clientId);

		if (!present) {

			String message = "Unerlaubter Zugriff auf eine Resource mit der ClientID=" + clientId;

			SecurityIncidentRegistered secEvent = new SecurityIncidentRegistered().withMessage(message);

			eventService.handleDomainEvent(secEvent);
		}

		return present;

	}

}
