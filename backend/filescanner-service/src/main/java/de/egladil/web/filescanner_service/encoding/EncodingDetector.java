// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.encoding;

import java.nio.charset.Charset;
import java.util.Optional;

import de.egladil.web.filescanner_service.encoding.impl.EncodingDetecorImpl;
import de.egladil.web.filescanner_service.scan.Upload;

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

	static EncodingDetector createForIntegrationTests() {

		return new EncodingDetecorImpl();
	}

}
