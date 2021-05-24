// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.scan.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.egladil.web.filescanner_api.domain.clamav.ClamAVService;
import de.egladil.web.filescanner_api.domain.clamav.VirusDetection;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.ScanResult;
import de.egladil.web.filescanner_api.domain.scan.ScanService;
import de.egladil.web.filescanner_api.domain.scan.Upload;
import de.egladil.web.filescanner_api.domain.securitychecks.ThreadDetection;
import de.egladil.web.filescanner_api.domain.securitychecks.ZipBombScanner;
import de.egladil.web.filescanner_api.domain.tika.TikaMediaTypeService;

/**
 * ScanServiceImpl
 */
@ApplicationScoped
public class ScanServiceImpl implements ScanService {

	@Inject
	ClamAVService clamAVService;

	@Inject
	TikaMediaTypeService tikaMediaTypeService;

	@Inject
	ZipBombScanner zipBombScanner;

	@Override
	public ScanResult scanFile(final ScanRequestPayload scanRequestPayload) {

		Upload upload = scanRequestPayload.getUpload();
		String ownerId = scanRequestPayload.getFileOwner();

		try {

			VirusDetection virusScanResult = clamAVService.scanFile(scanRequestPayload);

			if (virusScanResult.isVirusDetected()) {

				return new ScanResult().withVirusDetection(virusScanResult).withUserID(ownerId).withUploadName(upload.getName());

			}

			String mediaType = tikaMediaTypeService.detectMediaType(upload);

			ThreadDetection threadDetection = ThreadDetection.FALSE;

			if ("application/zip".equals(mediaType)) {

				threadDetection = zipBombScanner.checkForZipBomb(scanRequestPayload);
			}

			return new ScanResult().withUserID(ownerId).withUploadName(upload.getName()).withMediaType(mediaType)
				.withThreadDetection(threadDetection);
		} finally {

			upload.wipe();
		}
	}

}
