// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.events;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * MailService
 */
@ApplicationScoped
public class MailService {

	private static final String DEFAULT_DATE_MINUTES_FORMAT = "dd.MM.yyyy kk:mm";

	private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

	@ConfigProperty(name = "stage")
	String stage;

	@ConfigProperty(name = "email.to")
	String mailTo;

	@Inject
	Mailer mailer;

	public void sendMail(final FilescannerDomainEvent event) {

		// man kann über quarkus.mailer.mock steuern, ob tatsächlich Mails versendet werden.
		mailer.send(Mail.withText(mailTo,
			getSubject(new Date()),
			event.getMessagingPreview()));

		LOGGER.warn("mail has been sent");
	}

	String getSubject(final Date jetzt) {

		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_MINUTES_FORMAT);
		return stage + ": Filescanner Warnung " + sdf.format(jetzt);
	}

}
