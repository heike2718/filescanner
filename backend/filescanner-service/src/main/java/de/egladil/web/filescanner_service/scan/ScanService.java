// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.scan;

import de.egladil.web.filescanner_service.scan.impl.ScanServiceImpl;

/**
 * ScanService
 */
public interface ScanService {

	/**
	 * Unterzieht den gegebenen upoad einer Kette von Tests und gibt ein Gesamtergebnis zurück. Nur wenn die Datei kein Virus ist,
	 * wird der MIME-Type ermittelt und davon abhängig weitere gefährliche Dinge wie XMLExpansion, Zip-Bombe,... geprüft. Detections
	 * werden als EVENTS gespeichert und per Mail / Telegram-BOT gesendet.
	 *
	 * @param  scanRequestPayload
	 *                            ScanRequestPayload
	 * @return                    ScanResult
	 */
	ScanResult scanFile(ScanRequestPayload scanRequestPayload);

	/**
	 * Stellt eine initialisierte Implementierung für Integrationstests zur Verfügung.
	 *
	 * @return ScanService
	 */
	static ScanService createForIntegrationTest() {

		return ScanServiceImpl.createForIntegrationTest();
	}

}
