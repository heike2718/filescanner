// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.encoding.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.encoding.EncodingDetector;
import de.egladil.web.filescanner_api.domain.scan.Upload;

/**
 * EncodingDetecorImpl
 */
@RequestScoped
public class EncodingDetecorImpl implements EncodingDetector {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncodingDetecorImpl.class);

	@Override
	public Optional<Charset> detectEncoding(final Upload upload) {

		byte[] decoded = upload.getDecodedData();

		try (InputStreamReader ir = new InputStreamReader(new ByteArrayInputStream(decoded))) {

			String encoding = ir.getEncoding();

			Charset charset = Charset.forName(encoding);

			return Optional.of(charset);

		} catch (IOException e) {

			String msg = "konnte Encoding nicht ermitteln";
			LOGGER.error(msg + ": " + e.getMessage(), e);

			return Optional.empty();

		}
	}

}
