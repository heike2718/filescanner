// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.clamav;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;

import de.egladil.web.filescanner_api.domain.error.FilescannerRuntimeException;
import de.egladil.web.filescanner_api.domain.events.DomainEventService;
import de.egladil.web.filescanner_api.domain.events.VirusDetected;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.Upload;

import xyz.capybara.clamav.ClamavClient;
import xyz.capybara.clamav.InvalidResponseException;
import xyz.capybara.clamav.commands.scan.result.ScanResult;

/**
 * ClamAVService
 */
@ApplicationScoped
public class ClamAVService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClamAVService.class);

    @ConfigProperty(name = "clamav.host", defaultValue = "localhost")
    String host = null;

    @ConfigProperty(name = "clamav.port", defaultValue = "3310")
    String portAsString = null;

    @ConfigProperty(name = "clamav.timeout", defaultValue = "10000")
    String timeoutAsString = null;

    @Inject
    DomainEventService domainEventService;

    /**
     * @return boolean
     */
    public boolean checkAlive() {

        try {

            ClamavClient clamAVClient = new ClamavClient(host, Integer.valueOf(portAsString));
            try {
                clamAVClient.ping();
                return true;
            } catch (InvalidResponseException e) {
                LOGGER.error(e.getMessage());
                return false;
            }

        } catch (Exception e) {

            LOGGER.error("Unerwartete Exception: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Bemüht die ClamAV-CLI, den Upload zu Scannen.
     *
     * @param scanRequestPayload ScanRequestPayload
     * @return VirusDetection
     */
    public VirusDetection scanFile(final ScanRequestPayload scanRequestPayload) {

        LOGGER.info("sending File to host={}, port={}", host, portAsString);

        Upload upload = scanRequestPayload.getUpload();
        String ownerId = scanRequestPayload.getFileOwner();

        ClamavClient clamavClient = new ClamavClient(host, Integer.valueOf(portAsString));

        try (InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(upload.getDataBase64()))) {

            ScanResult scanResult = clamavClient.scan(in);

            if (!(scanResult instanceof ScanResult.OK)) {

                Map<String, Collection<String>> virusMap = ((ScanResult.VirusFound) scanResult).getFoundViruses();

                String viruses = "decetced viruses: " + StringUtils.join(virusMap.values(), ",");

                VirusDetected event = new VirusDetected()
                        .withFileName(upload.getName())
                        .withOwnerId(ownerId)
                        .withVirusScannerMessage(viruses)
                        .withClientId(scanRequestPayload.getClientId());

                domainEventService.handleDomainEvent(event);

                return new VirusDetection().withScannerMessage(viruses).withVirusDetected(true);

            }

            return new VirusDetection();

        } catch (Exception e) {

            LOGGER
                    .error(e.getClass().getSimpleName() + " beim Scannen: clamav-Konfiguration pruefen! "
                            + e.getMessage(), e);
            throw new FilescannerRuntimeException(
                    "Unerwartete Exception beim Scannen des Files " + upload.getName() + ": clientId="
                            + StringUtils.abbreviate(scanRequestPayload.getClientId(), 11) + ", ownerId" + ownerId);
        }
    }
}
