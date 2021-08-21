// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.mail.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.egladil.web.commons_mailer.CommonEmailService;
import de.egladil.web.commons_mailer.DefaultEmailDaten;
import de.egladil.web.commons_mailer.EmailServiceCredentials;
import de.egladil.web.commons_net.time.CommonTimeUtils;
import de.egladil.web.filescanner_service.mail.MailService;

/**
 * MailServiceImpl
 */
@ApplicationScoped
public class MailServiceImpl implements MailService {

	@ConfigProperty(name = "stage")
	String stage;

	@ConfigProperty(name = "email.host")
	String host;

	@ConfigProperty(name = "email.port")
	String port;

	@ConfigProperty(name = "email.user")
	String userName;

	@ConfigProperty(name = "email.password")
	String password;

	@ConfigProperty(name = "email.to")
	String mailTo;

	@Inject
	CommonEmailService commonMailService;

	@Override
	public boolean sendMessage(final String messageBody) {

		DefaultEmailDaten maildaten = new DefaultEmailDaten();
		maildaten.setEmpfaenger(mailTo);
		maildaten.setBetreff(getSubject(new Date()));
		maildaten.setText(messageBody);

		EmailServiceCredentials credentials = EmailServiceCredentials.createInstance(host, Integer.valueOf(port),
			userName, password.toCharArray(), userName);

		this.commonMailService.sendMail(maildaten, credentials);

		return true;
	}

	String getSubject(final Date jetzt) {

		SimpleDateFormat sdf = new SimpleDateFormat(CommonTimeUtils.DEFAULT_DATE_MINUTES_FORMAT);
		return stage + ": Filescanner Warnung " + sdf.format(jetzt);
	}

}
