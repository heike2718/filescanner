// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.clamav;

import de.egladil.web.filescanner_service.clamav.impl.ClamAVServiceImpl;
import de.egladil.web.filescanner_service.scan.ScanRequestPayload;

/**
 * ClamAVService
 */
public interface ClamAVService {

	/**
	 * Bemüht die ClamAV-CLI, den Upload zu Scannen.
	 *
	 * @param  scanRequestPayload
	 *                            ScanRequestPayload
	 * @return                    VirusDetection
	 */
	VirusDetection scanFile(ScanRequestPayload scanRequestPayload);

	static ClamAVService createForIntegrationTest() {

		return ClamAVServiceImpl.createForLocalTests();
	}

}
