// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.securitychecks.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.error.SecurityRuntimeException;

/**
 * TempFileHelper
 */
public class TempFileHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(TempFileHelper.class);

	/**
	 * Erzeugt ein temporäres File mit suffix '.txt'. Wandelt IOException in SecurityRuntimeException um.
	 *
	 * @param  name
	 *                                  String irgendein name
	 * @return                          File
	 * @throws SecurityRuntimeException
	 */
	public static File createTempFile(final String name) throws SecurityRuntimeException {

		return createTempFile(name, ".txt");
	}

	/**
	 * Erzeugt ein temporäres File. Wandelt IOException in SecurityRuntimeException um.
	 *
	 * @param  name
	 *                                  String irgendein name
	 * @param  suffix
	 *                                  file suffix including .
	 * @return                          File
	 * @throws SecurityRuntimeException
	 */
	public static File createTempFile(final String name, final String suffix) throws SecurityRuntimeException {

		if (suffix == null) {

			throw new NullPointerException("suffix");
		}

		try {

			return File.createTempFile(name + "-", suffix);
		} catch (IOException e) {

			LOGGER.error(e.getMessage(), e);
			throw new SecurityRuntimeException("unable to create tempfile: " + e.getMessage());

		}
	}

	/**
	 * Löscht das File und erzeugt Warungen, wenn es nicht gelöscht werden konnte.
	 *
	 * @param  tempFile
	 * @return
	 */
	public static boolean deleteQuietly(final File tempFile) {

		try {

			URI uri = new URI("file://" + tempFile.getAbsolutePath());
			Path path = Path.of(uri);
			java.nio.file.Files.delete(path);

			return true;
		} catch (Exception e) {

			LOGGER.warn("Konnte tempFile nicht löschen: path={}, exception={}", tempFile.getAbsolutePath(), e.getMessage(), e);
			return false;
		}

	}
}
