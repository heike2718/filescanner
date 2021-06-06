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
	void should_detectExcelTransformedFromOpenOffice() {

		// Arrange
		String filename = "auswertung_minikaenguru.xlsx";
		Upload upload = new Upload().withName(filename)
			.withData(TestFileUtils.loadDataQuietly("/" + filename));

		TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

		// Act
		String mediaType = service.detectMediaType(upload);

		System.out.println("\n");
		System.out.println(filename);
		System.out.println(mediaType);

		// Assert
		assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", mediaType);

	}

	@Test
	void should_detectExcel() {

		// Arrange
		String filename = "excel.xlsx";
		Upload upload = new Upload().withName(filename)
			.withData(TestFileUtils.loadDataQuietly("/" + filename));

		TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

		// Act
		String mediaType = service.detectMediaType(upload);

		System.out.println("\n");
		System.out.println(filename);
		System.out.println(mediaType);

		// Assert
		assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", mediaType);

	}

	@Test
	void should_detectExcelAltesFormat() {

		// Arrange
		String filename = "excel_altes_format.xls";
		Upload upload = new Upload().withName(filename)
			.withData(TestFileUtils.loadDataQuietly("/" + filename));

		TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

		// Act
		String mediaType = service.detectMediaType(upload);

		System.out.println("\n");
		System.out.println(filename);
		System.out.println(mediaType);

		// Assert
		assertEquals("application/vnd.ms-excel", mediaType);

	}

	@Test
	void should_detectOpenOfficeSpreadsheet() {

		// Arrange
		String filename = "auswertungstabelle_test.ods";
		Upload upload = new Upload().withName(filename)
			.withData(TestFileUtils.loadDataQuietly("/" + filename));

		TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

		// Act
		String mediaType = service.detectMediaType(upload);

		System.out.println("\n");
		System.out.println(filename);
		System.out.println(mediaType);

		// Assert
		assertEquals("application/vnd.oasis.opendocument.spreadsheet", mediaType);

	}

	@Test
	void should_detectOpenOfficeTextDocument() {

		// Arrange
		String filename = "textdokument.odt";
		Upload upload = new Upload().withName(filename)
			.withData(TestFileUtils.loadDataQuietly("/" + filename));

		TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

		// Act
		String mediaType = service.detectMediaType(upload);

		System.out.println("\n");
		System.out.println(filename);
		System.out.println(mediaType);

		// Assert
		assertEquals("application/vnd.oasis.opendocument.text", mediaType);

	}

	@Test
	void should_detectTextFile() {

		// Arrange
		String filename = "text.csv";
		Upload upload = new Upload().withName(filename)
			.withData(TestFileUtils.loadDataQuietly("/" + filename));

		TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

		// Act
		String mediaType = service.detectMediaType(upload);

		System.out.println("\n");
		System.out.println(filename);
		System.out.println(mediaType);

		// Assert
		assertEquals("text/plain", mediaType);

	}

}
