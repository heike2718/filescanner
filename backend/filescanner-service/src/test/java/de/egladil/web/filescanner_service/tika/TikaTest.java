// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.tika;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.nio.charset.Charset;

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

	@Test
	void should_decetctEncoding_when_odt() throws Exception {

		try (InputStream in = getClass().getResourceAsStream("/textdokument.odt")) {

			TikaConfig tika = new TikaConfig();

			Charset encoding = tika.getEncodingDetector().detect(
				TikaInputStream.get(in), new Metadata());

			assertEquals("windows-1252", encoding.displayName());

		}
	}

	@Test
	void should_decetctEncoding_when_ods() throws Exception {

		try (InputStream in = getClass().getResourceAsStream("/auswertungstabelle_test.ods")) {

			TikaConfig tika = new TikaConfig();

			Charset encoding = tika.getEncodingDetector().detect(
				TikaInputStream.get(in), new Metadata());

			assertEquals("IBM500", encoding.displayName());

		}
	}

	@Test
	void should_decetctEncoding_when_excel() throws Exception {

		try (InputStream in = getClass().getResourceAsStream("/auswertung_minikaenguru.xlsx")) {

			TikaConfig tika = new TikaConfig();

			Charset encoding = tika.getEncodingDetector().detect(
				TikaInputStream.get(in), new Metadata());

			assertEquals("windows-1252", encoding.displayName());

		}
	}
}
