// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.rest;

import de.egladil.web.filescanner_api.domain.clamav.ClamAVService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * PingResource
 */
@Path("ping")
public class PingResource {

	@Inject
	ClamAVService clamAVService;

	@GET
	public Response pong() {

		boolean isAlive = clamAVService.checkAlive();

		return isAlive ? Response.ok().build() : Response.status(503).build();

	}
}
