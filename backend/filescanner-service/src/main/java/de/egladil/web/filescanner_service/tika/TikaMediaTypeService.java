// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.tika;

import de.egladil.web.filescanner_service.scan.Upload;
import de.egladil.web.filescanner_service.tika.impl.TikaMediaTypeServiceImpl;

/**
 * TikaMediaTypeService
 */
public interface TikaMediaTypeService {

	/**
	 * Ermittelt den Mediatype.
	 *
	 * @param  upload
	 * @return        String
	 */
	String detectMediaType(Upload upload);

	static TikaMediaTypeService createForIntegrationTest() {

		return new TikaMediaTypeServiceImpl();
	}
}
