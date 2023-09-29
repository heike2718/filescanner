// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_service.telegram.impl;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_service.telegram.TelegramMessageService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * TelegramMessageServiceImpl
 */
@ApplicationScoped
public class TelegramMessageServiceImpl implements TelegramMessageService {

	private static final Logger LOG = LoggerFactory.getLogger(TelegramMessageServiceImpl.class);

	@ConfigProperty(name = "telegram.chatId")
	String chatId;

	@ConfigProperty(name = "telegram.secret")
	String secret;

	@Override
	public boolean sendMessage(final String messageBody) {

		// Messager messager = Messager.createMessageSenderOfType(MessagerType.TELEGRAM);
		//
		// try {
		//
		// Properties secretProps = new Properties();
		// secretProps.put(TelegramConfigKeys.QUERY_PARAM_CHAT_ID, chatId);
		// secretProps.put(TelegramConfigKeys.SECRET, secret);
		//
		// Map<String, String> configuration = messager.buildConfiguration(secretProps);
		//
		// messager.sendMessage(messageBody, configuration);
		//
		// return true;
		//
		// } catch (Exception e) {
		//
		// String configDescription = messager.getConfigurationDescription().print();
		// LOG.error("Message konnte nicht versendet werden: {} - TelegramConfiguration pruefen: {}", e.getMessage(),
		// configDescription, e);
		//
		// return false;
		//
		// }

		return true;

	}

}
