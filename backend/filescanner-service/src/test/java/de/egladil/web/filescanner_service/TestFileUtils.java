// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service;

import java.io.IOException;
import java.io.InputStream;

/**
 * TestFileUtils
 */
public class TestFileUtils {

	public static byte[] loadDataQuietly(final String classpathLocation) {

		System.out.println(classpathLocation);

		try (InputStream in = TestFileUtils.class.getResourceAsStream(classpathLocation)) {

			return in.readAllBytes();
		} catch (IOException e) {

			throw new RuntimeException("Test nicht möglich: " + e.getMessage(), e);
		}

	}

}
