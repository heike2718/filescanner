// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.tika.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.egladil.web.filescanner_service.TestFileUtils;
import de.egladil.web.filescanner_service.scan.Upload;

/**
 * TikaMediaTypeServiceImplTest
 */
public class TikaMediaTypeServiceImplTest {

	@Nested
	class MediaTypeTests {

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
		void should_complain_aboutExcelAltesFormat() {

			// Arrange
			String filename = "excel_altes_format.xls";
			Upload upload = new Upload().withName(filename)
				.withData(TestFileUtils.loadDataQuietly("/" + filename));

			TikaMediaTypeServiceImpl service = new TikaMediaTypeServiceImpl();

			// Act
			try {

				service.detectMediaType(upload);
				fail("kein NoSuchFieldError");
			} catch (NoSuchFieldError e) {

				assertEquals("WORKBOOK_DIR_ENTRY_NAMES", e.getMessage());
			}

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

}
