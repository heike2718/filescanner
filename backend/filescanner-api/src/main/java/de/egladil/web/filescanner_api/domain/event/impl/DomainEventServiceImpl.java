// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.event.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.event.DomainEventService;
import de.egladil.web.filescanner_api.domain.event.FilescannerDomainEvent;
import de.egladil.web.filescanner_api.domain.mail.MailService;
import de.egladil.web.filescanner_api.domain.telegram.TelegramMessageService;

/**
 * DomainEventHandler
 */
@ApplicationScoped
public class DomainEventServiceImpl implements DomainEventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventServiceImpl.class);

	@ConfigProperty(name = "email.activated", defaultValue = "false")
	String mailActive;

	@ConfigProperty(name = "telegram.activated", defaultValue = "false")
	String telegramActive;

	@Inject
	TelegramMessageService telegramService;

	@Inject
	MailService mailService;

	@Override
	public void handleDomainEvent(@Observes final FilescannerDomainEvent event) {

		LOGGER.warn(event.serializeQuietly());

		if (isMailActivated()) {

			mailService.sendMessage(event.getMessagingPreview());
		}

		if (isTelegramActivated()) {

			telegramService.sendMessage(event.getMessagingPreview());
		}

	}

	private boolean isMailActivated() {

		return Boolean.valueOf(mailActive);
	}

	private boolean isTelegramActivated() {

		return Boolean.valueOf(telegramActive);
	}

}
