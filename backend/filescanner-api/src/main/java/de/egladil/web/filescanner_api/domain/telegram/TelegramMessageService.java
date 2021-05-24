// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.telegram;

/**
 * TelegramMessageService
 */
public interface TelegramMessageService {

	/**
	 * Sendet eine Nachricht an meinen TelegramChat
	 *
	 * @param  messageBody
	 * @return
	 */
	boolean sendMessage(String messageBody);

}
