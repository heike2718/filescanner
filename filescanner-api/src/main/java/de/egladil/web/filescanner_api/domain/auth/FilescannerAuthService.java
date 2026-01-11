// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.apache.commons.lang3.StringUtils;

import de.egladil.web.filescanner_api.domain.events.DomainEventService;
import de.egladil.web.filescanner_api.domain.events.FilescannerSecurityIncidentRegistered;

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

        if (StringUtils.isBlank(clientId)) {

            String message = "Unerlaubter Zugriff auf eine Resource mit leerer clientId=";

            FilescannerSecurityIncidentRegistered secEvent = new FilescannerSecurityIncidentRegistered()
                    .withMessage(message);

            eventService.handleDomainEvent(secEvent);

            return false;
        }

        boolean present = new ClientIdFilter(clientIds).isKnownClientId(clientId);

        if (!present) {

            String message = "Unerlaubter Zugriff auf eine Resource mit der ClientID=" + clientId;

            FilescannerSecurityIncidentRegistered secEvent = new FilescannerSecurityIncidentRegistered()
                    .withMessage(message);

            eventService.handleDomainEvent(secEvent);
        }

        return present;

    }

}
