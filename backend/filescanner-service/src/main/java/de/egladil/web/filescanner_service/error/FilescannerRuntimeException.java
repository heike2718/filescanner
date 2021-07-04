// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.error;

/**
 * FilescannerRuntimeException
 */
public class FilescannerRuntimeException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public FilescannerRuntimeException(final String message, final Throwable cause) {

		super(message, cause);

	}

	/**
	 * @param message
	 */
	public FilescannerRuntimeException(final String message) {

		super(message);

	}

}
