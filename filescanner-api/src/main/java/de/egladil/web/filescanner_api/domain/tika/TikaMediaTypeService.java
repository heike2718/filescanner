// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.tika;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.error.FilescannerRuntimeException;
import de.egladil.web.filescanner_api.domain.scan.Upload;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * TikaMediaTypeService
 */
@ApplicationScoped
public class TikaMediaTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TikaMediaTypeService.class);

	/**
	 * Ermittelt den Mediatype.
	 *
	 * @param  upload
	 * @return        String
	 */
	public String detectMediaType(final Upload upload) {

		try (InputStream in = new ByteArrayInputStream(upload.getDecodedData())) {

			TikaConfig tika = new TikaConfig();

			MediaType mediaType = tika.getDetector().detect(
				TikaInputStream.get(in), new Metadata());

			return mediaType.toString();

		} catch (TikaException | IOException e) {

			String msg = "konnte MediaType nicht ermitteln";
			LOGGER.error(msg + ": " + e.getMessage(), e);
			throw new FilescannerRuntimeException(msg);
		}

	}
}
