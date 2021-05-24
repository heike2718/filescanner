// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.securitychecks;

import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;

/**
 * ZipBombScanner
 */
public interface ZipBombScanner {

	/**
	 * Wendett Heuristik an, um auf eine Zip-Bombe zu schließen.
	 *
	 * @param  scanRequestPayload
	 *                            ScanRequestPayload
	 * @return                    ThreadDetection
	 */
	ThreadDetection checkForZipBomb(ScanRequestPayload scanRequestPayload);

}
