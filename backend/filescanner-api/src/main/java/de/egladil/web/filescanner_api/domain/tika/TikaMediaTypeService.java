// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.tika;

import de.egladil.web.filescanner_api.domain.scan.Upload;

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

}
