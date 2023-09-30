// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.clamav;

import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.error.FilescannerRuntimeException;
import de.egladil.web.filescanner_api.domain.events.DomainEventService;
import de.egladil.web.filescanner_api.domain.events.VirusDetected;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.Upload;
import fi.solita.clamav.ClamAVClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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

			ClamAVClient clamAVClient = new ClamAVClient(host, Integer.valueOf(portAsString), Integer.valueOf(timeoutAsString));

			return clamAVClient.ping();
		} catch (Exception e) {

			LOGGER.error("Unerwartete Exception: " + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Bemüht die ClamAV-CLI, den Upload zu Scannen.
	 *
	 * @param  scanRequestPayload
	 *                            ScanRequestPayload
	 * @return                    VirusDetection
	 */
	public VirusDetection scanFile(final ScanRequestPayload scanRequestPayload) {

		LOGGER.info("sending File to host={}, port={}", host, portAsString);

		Upload upload = scanRequestPayload.getUpload();
		String ownerId = scanRequestPayload.getFileOwner();

		try {

			ClamAVClient clamAVClient = new ClamAVClient(host, Integer.valueOf(portAsString), Integer.valueOf(timeoutAsString));

			byte[] data = Base64.getDecoder().decode(upload.getDataBase64());
			String scanResult = new String(clamAVClient.scan(data));

			if (!scanResult.toUpperCase().contains("OK")) {

				// der clamAVClient packt ein ominöses nicht druckbares ASCII-Zeichen ans Ende, das im json nicht schön aussieht.
				// schneiden wir ab.
				String scannerText = scanResult.substring(0, scanResult.length() - 1);

				VirusDetected event = new VirusDetected().withFileName(upload.getName()).withOwnerId(ownerId)
					.withVirusScannerMessage(scannerText).withClientId(scanRequestPayload.getClientId());

				domainEventService.handleDomainEvent(event);

				return new VirusDetection().withScannerMessage(scannerText).withVirusDetected(true);

			}

			return new VirusDetection();

		} catch (Exception e) {

			LOGGER.error(e.getClass().getSimpleName() + " beim Scannen: clamav-Konfiguration pruefen! " + e.getMessage(), e);
			throw new FilescannerRuntimeException(
				"Unerwartete Exception beim Scannen des Files " + upload.getName() + ": clientId="
					+ StringUtils.abbreviate(scanRequestPayload.getClientId(), 11) + ", ownerId" + ownerId);
		}
	}
}
