// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.auth;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * ClientIdFilter
 */
public class ClientIdFilter {

	private final List<String> clientIds;

	ClientIdFilter(final String clientIds) {

		String[] tokens = StringUtils.split(clientIds, ",");
		this.clientIds = Arrays.asList(tokens);
	}

	boolean isKnownClientId(final String clientId) {

		return this.clientIds.stream().filter(id -> id.equals(clientId)).findFirst().isPresent();
	}

}
