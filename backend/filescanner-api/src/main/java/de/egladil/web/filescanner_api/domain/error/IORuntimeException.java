// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.error;

/**
 * IORuntimeException
 */
public class IORuntimeException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public IORuntimeException(final String message) {

		super(message);

	}

	public IORuntimeException(final String message, final Throwable cause) {

		super(message, cause);

	}

}
