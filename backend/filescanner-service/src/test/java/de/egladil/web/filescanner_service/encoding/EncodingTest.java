// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.encoding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

/**
 * EncodingTest
 */
public class EncodingTest {

	@Test
	void should_detectTheCorrrectEncoding_when_ods() throws Exception {

		try (InputStream in = getClass().getResourceAsStream("/auswertungstabelle_test.ods");
			InputStreamReader ir = new InputStreamReader(in)) {

			String encoding = ir.getEncoding();

			assertEquals("UTF8", encoding);

		}

	}

	@Test
	void should_detectTheCorrrectEncoding_when_odt() throws Exception {

		try (InputStream in = getClass().getResourceAsStream("/textdokument.odt");
			InputStreamReader ir = new InputStreamReader(in)) {

			String encoding = ir.getEncoding();

			assertEquals("UTF8", encoding);

		}

	}

}
