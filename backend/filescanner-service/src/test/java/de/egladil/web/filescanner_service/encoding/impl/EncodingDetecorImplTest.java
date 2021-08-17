// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.encoding.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import de.egladil.web.filescanner_service.TestFileUtils;
import de.egladil.web.filescanner_service.scan.Upload;

/**
 * EncodingDetecorImplTest
 */
public class EncodingDetecorImplTest {

	@Test
	void should_detectTheCorrrectEncoding_when_ods() throws Exception {

		// Arrange
		Upload upload = new Upload().withData(TestFileUtils.loadDataQuietly("/auswertungstabelle_test.ods"))
			.withName("auswertungstabelle_test.ods");

		// Act
		Optional<Charset> optCharset = new EncodingDetecorImpl().detectEncoding(upload);

		// Assert
		assertTrue(optCharset.isPresent());

		Charset charset = optCharset.get();
		assertEquals(Charset.forName("UTF-8"), charset);

	}

	@Test
	void should_detectTheCorrrectEncoding_when_excelWithKyrillisch() throws Exception {

		// Arrange
		Upload upload = new Upload().withData(TestFileUtils.loadDataQuietly("/auswertung_minikaenguru-utf8.xlsx"))
			.withName("auswertung_minikaenguru-utf8.xlsx");

		// Act
		Optional<Charset> optCharset = new EncodingDetecorImpl().detectEncoding(upload);

		// Assert
		assertTrue(optCharset.isPresent());

		Charset charset = optCharset.get();
		assertEquals(Charset.forName("UTF-8"), charset);

	}

	@Test
	void should_detectTheCorrrectEncoding_when_odt() throws Exception {

		// Arrange
		Upload upload = new Upload().withData(TestFileUtils.loadDataQuietly("/textdokument.odt"))
			.withName("textdokument.odt");

		// Act
		Optional<Charset> optCharset = new EncodingDetecorImpl().detectEncoding(upload);

		// Assert
		assertTrue(optCharset.isPresent());

		Charset charset = optCharset.get();
		assertEquals(Charset.forName("UTF-8"), charset);
	}

}
