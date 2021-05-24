// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.tika;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.junit.jupiter.api.Test;

/**
 * TikaTest
 */
public class TikaTest {

	@Test
	void should_detectZipBombAsZip() throws Exception {

		try (InputStream in = getClass().getResourceAsStream("/zipbomb-decompress-at-your-own-risk.txt")) {

			TikaConfig tika = new TikaConfig();

			MediaType mimetype = tika.getDetector().detect(
				TikaInputStream.get(in), new Metadata());

			assertEquals("application/zip", mimetype.toString());
		}
	}
}
