// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.securitychecks;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.egladil.web.filescanner_api.domain.events.DomainEventService;
import de.egladil.web.filescanner_api.domain.events.ZipBombDetected;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.Upload;
import de.egladil.web.filescanner_api.domain.securitychecks.impl.NonRecursiveZipCompressionRatioComputer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * ZipBombScanner
 */
@ApplicationScoped
public class ZipBombScanner {

	@ConfigProperty(name = "zip.max_expected_compression_ratio", defaultValue = "100")
	String maxExpectedCompressionRatioStr;

	@Inject
	DomainEventService domainEventService;

	public static ZipBombScanner createForTest() {

		ZipBombScanner result = new ZipBombScanner();
		result.maxExpectedCompressionRatioStr = "100";
		return result;
	}

	/**
	 * Wendet Heuristik an, um auf eine Zip-Bombe zu schließen.
	 *
	 * @param  scanRequestPayload
	 *                            ScanRequestPayload
	 * @return                    ThreadDetection
	 */
	public ThreadDetection checkForZipBomb(final ScanRequestPayload scanRequestPayload) {

		String ownerId = scanRequestPayload.getFileOwner();
		Upload upload = scanRequestPayload.getUpload();

		long compressionRatio = new NonRecursiveZipCompressionRatioComputer().getCompressionRatio(ownerId, upload.getDecodedData());

		long maxExpectedCompressionRatio = Long.valueOf(maxExpectedCompressionRatioStr);

		if (compressionRatio >= maxExpectedCompressionRatio) {

			ZipBombDetected event = new ZipBombDetected().withCompressionRatio(compressionRatio).withFileName(upload.getName())
				.withOwnerId(ownerId).withClientId(scanRequestPayload.getClientId());

			if (domainEventService != null) {

				domainEventService.handleDomainEvent(event);

			}

			return new ThreadDetection().withSecurityCheckMessage("Verdacht auf ZIP-Bombe").withSecurityThreadDetected(true);
		}

		return new ThreadDetection();
	}

}
