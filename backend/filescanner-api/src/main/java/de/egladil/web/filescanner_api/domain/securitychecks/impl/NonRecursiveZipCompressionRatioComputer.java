// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.securitychecks.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.error.SecurityRuntimeException;

/**
 * NonRecursiveZipCompressionRatioComputer ermittelt die Kompressionsrate aller Dateien in der ersten Ebene eines Zip-Containers.
 * Unterverzeichnisse werden ignoriert.
 */
public class NonRecursiveZipCompressionRatioComputer {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonRecursiveZipCompressionRatioComputer.class);

	/**
	 * Ermittelt den Kompressionsfaktor eines ZipFiles.
	 *
	 * @param  upload
	 *                Upload
	 * @return        long
	 */
	public long getCompressionRatio(final String ownerId, final byte[] data) {

		File tempFile = TempFileHelper.createTempFile(ownerId);

		try (InputStream in = new ByteArrayInputStream(data); FileOutputStream out = new FileOutputStream(tempFile)) {

			IOUtils.copy(in, out);
			long uncompressedSize = getUncompressedSize(tempFile);
			return uncompressedSize / data.length;
		} catch (IOException e) {

			LOGGER.error(e.getMessage(), e);
			throw new SecurityRuntimeException("IOException beim Ermitteln des compression ratio: " + e.getMessage());

		} finally {

			TempFileHelper.deleteQuietly(tempFile);
		}
	}

	/**
	 * If it is a ZipFile, this method returns the size of the uncompressed data as sum of the size of uncompressed data in each
	 * ZipEntry.
	 *
	 * @param  file
	 * @return             long
	 * @throws IOException
	 */
	public long getUncompressedSize(final File file) throws IOException {

		long size = 0;

		try (ZipFile zipFile = new ZipFile(file)) {

			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {

				ZipEntry entry = entries.nextElement();

				if (!entry.isDirectory()) {

					size += entry.getSize();
				} else {

					LOGGER.debug("ZipEntry {}  ist Verzeichnis und wird ignoriert", entry.getName());
				}

			}

		}

		return size;

	}

}
