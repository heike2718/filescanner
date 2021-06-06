// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.tika.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.egladil.web.filescanner_api.domain.TestFileUtils;
import de.egladil.web.filescanner_api.domain.scan.Upload;

/**
 * TikaMediaTypeServiceImplTest
 */
public class TikaMediaTypeServiceImplTest {

	@Test
	void should_detectOpenOfficeSpreadsheet() {

		// Arrange
		Upload upload = new Upload().withName("auswertungstabelle_test.ods")
			.withData(TestFileUtils.loadDataQuietly("/auswertungstabelle_test.ods"));

		TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

		// Act
		String mediaType = service.detectMediaType(upload);

		// Assert
		assertEquals("application/vnd.oasis.opendocument.spreadsheet", mediaType);

	}

	@Test
	void should_detectOpenOffice() {

		// Arrange
		Upload upload = new Upload().withName("textdokument.odt")
			.withData(TestFileUtils.loadDataQuietly("/textdokument.odt"));

		TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

		// Act
		String mediaType = service.detectMediaType(upload);

		// Assert
		assertEquals("application/vnd.oasis.opendocument.text", mediaType);

	}

}
