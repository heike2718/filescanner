// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.event.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_service.event.DomainEventService;
import de.egladil.web.filescanner_service.event.FilescannerDomainEvent;
import de.egladil.web.filescanner_service.mail.MailService;
import de.egladil.web.filescanner_service.telegram.TelegramMessageService;

/**
 * DomainEventHandler
 */
@ApplicationScoped
public class FilescannerDomainEventServiceImpl implements DomainEventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilescannerDomainEventServiceImpl.class);

	@ConfigProperty(name = "email.activated", defaultValue = "false")
	String mailActive;

	@ConfigProperty(name = "telegram.activated", defaultValue = "false")
	String telegramActive;

	@Inject
	TelegramMessageService telegramService;

	@Inject
	MailService mailService;

	private boolean test = false;

	public static DomainEventService createForIntegrationTests() {

		FilescannerDomainEventServiceImpl result = new FilescannerDomainEventServiceImpl();
		result.mailActive = "false";
		result.telegramActive = "false";
		result.test = true;
		return result;

	}

	@Override
	public void handleDomainEvent(@Observes final FilescannerDomainEvent event) {

		String serializedEvent = event.serializeQuietly();
		LOGGER.warn(serializedEvent);

		if (isMailActivated()) {

			mailService.sendMessage(event.getMessagingPreview());
		}

		if (isTelegramActivated()) {

			telegramService.sendMessage(event.getMessagingPreview());
		}

		if (test) {

			System.out.println(serializedEvent);
		}

	}

	private boolean isMailActivated() {

		return Boolean.valueOf(mailActive);
	}

	private boolean isTelegramActivated() {

		return Boolean.valueOf(telegramActive);
	}

}
