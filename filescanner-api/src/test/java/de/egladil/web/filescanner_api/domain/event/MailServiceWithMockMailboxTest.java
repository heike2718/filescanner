// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.egladil.web.filescanner_api.domain.events.MailService;
import de.egladil.web.filescanner_api.domain.events.VirusDetected;
import de.egladil.web.filescanner_api.profiles.MockMailerProfile;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.vertx.ext.mail.MailMessage;
import jakarta.inject.Inject;

/**
 * MailServiceWithMockMailboxTest
 */
@QuarkusTest
@TestProfile(MockMailerProfile.class)
public class MailServiceWithMockMailboxTest {

	@ConfigProperty(name = "email.to")
	String to;

	@ConfigProperty(name = "quarkus.mailer.from")
	String from;

	@Inject
	MockMailbox mailbox;

	@Inject
	MailService mailService;

	@BeforeEach
	void init() {

		mailbox.clear();
	}

	@Test
	void should_sendAMail() {

		// Arrange
		VirusDetected event = new VirusDetected().withClientId("client-id").withFileName("evil.txt").withOwnerId("ichedoche")
			.withVirusScannerMessage("da ist ein virus");

		// Act
		mailService.sendMail(event);

		List<MailMessage> mailMessagesSentTo = mailbox.getMailMessagesSentTo(to);
		assertEquals(1, mailMessagesSentTo.size());
		MailMessage actual = mailMessagesSentTo.get(0);
		assertEquals(from, actual.getFrom());
		assertEquals(
			"VirusDetected [clientId=client-id, ownerId=ichedoche, fileName=evil.txt, virusScannerMessage=da ist ein virus]",
			actual.getText());
	}

}
