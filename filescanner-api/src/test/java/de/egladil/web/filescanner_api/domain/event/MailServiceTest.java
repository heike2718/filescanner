// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.event;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import de.egladil.web.filescanner_api.domain.events.MailService;
import de.egladil.web.filescanner_api.domain.events.VirusDetected;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * MailServiceTest
 */
@QuarkusTest
public class MailServiceTest {

    @Inject
    MailService mailService;

    @Test
    void should_sendAMail() {

        // Arrange
        VirusDetected event = new VirusDetected()
                .withClientId("client-id")
                .withFileName("evil.txt")
                .withOwnerId("ichedoche")
                .withVirusScannerMessage("dies ist eine Mail aus dem filescannertest");

        // Act
        try {

            mailService.sendMail(event);
        } catch (Exception e) {

            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
