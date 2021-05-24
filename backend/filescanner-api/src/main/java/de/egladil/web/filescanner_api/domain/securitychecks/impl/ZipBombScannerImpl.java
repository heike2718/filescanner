// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.securitychecks.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.egladil.web.commons_security.zip.NonRecursiveZipCompressionRatioComputer;
import de.egladil.web.filescanner_api.domain.event.DomainEventService;
import de.egladil.web.filescanner_api.domain.event.ZipBombDetected;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.Upload;
import de.egladil.web.filescanner_api.domain.securitychecks.ThreadDetection;
import de.egladil.web.filescanner_api.domain.securitychecks.ZipBombScanner;

/**
 * ZipBombScannerImpl
 */
@ApplicationScoped
public class ZipBombScannerImpl implements ZipBombScanner {

	@ConfigProperty(name = "zip.max_expected_compression_ratio", defaultValue = "100")
	String maxExpectedCompressionRatioStr;

	@Inject
	DomainEventService domainEventService;

	@Override
	public ThreadDetection checkForZipBomb(final ScanRequestPayload scanRequestPayload) {

		String ownerId = scanRequestPayload.getFileOwner();
		Upload upload = scanRequestPayload.getUpload();

		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(ownerId, upload.getDecodedData());

		long maxExpectedCompressionRatio = Long.valueOf(maxExpectedCompressionRatioStr);

		if (compressionRatio >= maxExpectedCompressionRatio) {

			ZipBombDetected event = new ZipBombDetected().withCompressionRatio(compressionRatio).withFileName(upload.getName())
				.withOwnerId(ownerId).withClientId(scanRequestPayload.getClientId());

			domainEventService.handleDomainEvent(event);

			return ThreadDetection.TRUE.withSecurityCheckMessage("Verdacht auf ZIP-Bombe");
		}

		return ThreadDetection.FALSE;
	}

}
