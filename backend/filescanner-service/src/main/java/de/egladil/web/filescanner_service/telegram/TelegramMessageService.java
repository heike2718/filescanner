// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.telegram;

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
