// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.error;

/**
 * FilescannerSecurityException
 */
public class FilescannerSecurityException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public FilescannerSecurityException(final String message) {

		super(message);

	}

}
