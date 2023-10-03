// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.scan;

import java.util.Arrays;
import java.util.List;

import de.egladil.web.filescanner_api.domain.clamav.ClamAVService;
import de.egladil.web.filescanner_api.domain.clamav.VirusDetection;
import de.egladil.web.filescanner_api.domain.securitychecks.ThreadDetection;
import de.egladil.web.filescanner_api.domain.securitychecks.ZipBombScanner;
import de.egladil.web.filescanner_api.domain.tika.TikaMediaTypeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * ScanService
 */
@ApplicationScoped
public class ScanService {

	private static final List<String> ZIP_CONTAINER_TYPES = Arrays
		.asList(new String[] { "application/zip", "application/vnd.oasis.opendocument.spreadsheet",
			"application/vnd.oasis.opendocument.text" });

	@Inject
	ClamAVService clamAVService;

	@Inject
	TikaMediaTypeService tikaMediaTypeService;

	@Inject
	ZipBombScanner zipBombScanner;

	/**
	 * Unterzieht den gegebenen upoad einer Kette von Tests und gibt ein Gesamtergebnis zurück. Nur wenn die Datei kein Virus ist,
	 * wird der MIME-Type ermittelt und davon abhängig weitere gefährliche Dinge wie XMLExpansion, Zip-Bombe,... geprüft. Detections
	 * werden als EVENTS gespeichert und per Mail / Telegram-BOT gesendet.
	 *
	 * @param  scanRequestPayload
	 *                            ScanRequestPayload
	 * @return                    ScanResult
	 */
	public ScanResult scanFile(final ScanRequestPayload scanRequestPayload) {

		Upload upload = scanRequestPayload.getUpload();
		String ownerId = scanRequestPayload.getFileOwner();

		try {

			VirusDetection virusScanResult = clamAVService.scanFile(scanRequestPayload);

			if (virusScanResult.isVirusDetected()) {

				return new ScanResult().withVirusDetection(virusScanResult).withUserID(ownerId).withUploadName(upload.getName());

			}

			String mediaType = tikaMediaTypeService.detectMediaType(upload);

			ThreadDetection threadDetection = new ThreadDetection();

			if (ZIP_CONTAINER_TYPES.contains(mediaType)) {

				threadDetection = zipBombScanner.checkForZipBomb(scanRequestPayload);
			}

			ScanResult result = new ScanResult().withUserID(ownerId).withUploadName(upload.getName()).withMediaType(mediaType)
				.withThreadDetection(threadDetection).withVirusDetection(new VirusDetection());

			return result;
		} finally {

			upload.wipe();
		}
	}

}
