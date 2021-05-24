// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.clamav;

import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;

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

}
