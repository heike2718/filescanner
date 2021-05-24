// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.clamav.impl;

import java.util.Base64;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.clamav.ClamAVService;
import de.egladil.web.filescanner_api.domain.clamav.VirusDetection;
import de.egladil.web.filescanner_api.domain.error.FilescannerRuntimeException;
import de.egladil.web.filescanner_api.domain.event.DomainEventService;
import de.egladil.web.filescanner_api.domain.event.VirusDetected;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.Upload;
import fi.solita.clamav.ClamAVClient;

/**
 * ClamAVServiceImpl
 */
@ApplicationScoped
public class ClamAVServiceImpl implements ClamAVService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClamAVServiceImpl.class);

	@ConfigProperty(name = "clamav.host")
	String host = null;

	@ConfigProperty(name = "clamav.port", defaultValue = "3310")
	String portAsString = null;

	@ConfigProperty(name = "clamav.timeout", defaultValue = "5000")
	String timeoutAsString = null;

	@Inject
	DomainEventService domainEventService;

	@Override
	public VirusDetection scanFile(final ScanRequestPayload scanRequestPayload) {

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

				return VirusDetection.TRUE.withScannerMessage(scannerText);

			}

			return VirusDetection.FALSE;

		} catch (Exception e) {

			LOGGER.error("Unerwartete Exception: " + e.getMessage(), e);
			throw new FilescannerRuntimeException(
				"Unerwartete Exception beim Scannen des Files " + upload.getName() + ": clientId="
					+ StringUtils.abbreviate(scanRequestPayload.getClientId(), 11) + ", ownerId" + ownerId);
		}
	}
}
