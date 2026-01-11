//=====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
//=====================================================
package de.egladil.web.filescanner_api.domain.error;

/**
 *
 */
public class UnsupportedVersionException extends RuntimeException {

	private static final long serialVersionUID = 159570194885666964L;

	/**
	 * @param message
	 */
	public UnsupportedVersionException(String message) {
		super(message);
	}
}
