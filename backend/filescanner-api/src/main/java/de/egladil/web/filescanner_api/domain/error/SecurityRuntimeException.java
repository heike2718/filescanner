// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.error;

/**
 * SecurityRuntimeException
 */
public class SecurityRuntimeException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public SecurityRuntimeException(final String message, final Throwable cause) {

		super(message, cause);

	}

	/**
	 * @param message
	 */
	public SecurityRuntimeException(final String message) {

		super(message);

	}

}
