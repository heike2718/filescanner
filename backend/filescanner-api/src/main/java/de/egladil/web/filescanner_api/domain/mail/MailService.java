// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.mail;

/**
 * MailService
 */
public interface MailService {

	/**
	 * Sendet eine Nachricht an mein Mailaccount.
	 *
	 * @param  messageBody
	 * @return             boolean
	 */
	boolean sendMessage(String messageBody);
}
