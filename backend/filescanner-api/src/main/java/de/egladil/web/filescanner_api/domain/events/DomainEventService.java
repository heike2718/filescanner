// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.events;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * DomainEventHandler
 */
@ApplicationScoped
public class DomainEventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventService.class);

	@ConfigProperty(name = "telegram.activated", defaultValue = "false")
	String telegramActive;

	// @Inject
	// TelegramMessageService telegramService;

	@Inject
	MailService mailService;

	/**
	 * Tut, was mit einem FilescannerDomainEvent event getan werden muss.
	 *
	 * @param event
	 */
	public void handleDomainEvent(final FilescannerDomainEvent event) {

		String serializedEvent = event.serializeQuietly();
		LOGGER.warn(serializedEvent);

		this.mailService.sendMail(event);

		// if (isTelegramActivated()) {
		//
		// telegramService.sendMessage(event.getMessagingPreview());
		// }

	}

	boolean isTelegramActivated() {

		return Boolean.valueOf(telegramActive);
	}

}
