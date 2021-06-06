// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.encoding;

import java.nio.charset.Charset;
import java.util.Optional;

import de.egladil.web.filescanner_api.domain.scan.Upload;

/**
 * EncodingDetector
 */
public interface EncodingDetector {

	/**
	 * Ermittelt das wahrscheinliche Encoding.
	 *
	 * @param  upload
	 * @return        Charset
	 */
	Optional<Charset> detectEncoding(Upload upload);

}
