// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.scan.impl;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.egladil.web.filescanner_api.domain.clamav.ClamAVService;
import de.egladil.web.filescanner_api.domain.clamav.VirusDetection;
import de.egladil.web.filescanner_api.domain.encoding.EncodingDetector;
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

	private static final List<String> ZIP_CONTAINER_TYPES = Arrays
		.asList(new String[] { "application/zip", "application/vnd.oasis.opendocument.spreadsheet",
			"application/vnd.oasis.opendocument.text" });

	@Inject
	ClamAVService clamAVService;

	@Inject
	TikaMediaTypeService tikaMediaTypeService;

	@Inject
	ZipBombScanner zipBombScanner;

	@Inject
	EncodingDetector encodingDetector;

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

			if (ZIP_CONTAINER_TYPES.contains(mediaType)) {

				threadDetection = zipBombScanner.checkForZipBomb(scanRequestPayload);
			}

			Optional<Charset> optCharset = encodingDetector.detectEncoding(upload);

			ScanResult result = new ScanResult().withUserID(ownerId).withUploadName(upload.getName()).withMediaType(mediaType)
				.withThreadDetection(threadDetection);

			if (optCharset.isPresent()) {

				result.setCharset(optCharset.get().name());
			}

			return result;
		} finally {

			upload.wipe();
		}
	}

}
