// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.commons_validation.payload.MessagePayload;
import de.egladil.web.commons_validation.payload.ResponsePayload;
import de.egladil.web.filescanner_api.domain.auth.FilescannerAuthService;
import de.egladil.web.filescanner_api.domain.error.ClientAuthException;
import de.egladil.web.filescanner_service.scan.ScanRequestPayload;
import de.egladil.web.filescanner_service.scan.ScanResult;
import de.egladil.web.filescanner_service.scan.ScanService;

@Path("/")
public class FilescannerResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilescannerResource.class);

	@Inject
	FilescannerAuthService authService;

	@Inject
	ScanService scanService;

	@GET
	@Path("hello")
	public Response hello() {

		return Response.ok(ResponsePayload.messageOnly(MessagePayload.info(
			"Hallo hier spricht die FilescannerResource der filescanner-api vom Port 9800. Um eine Datei zu scannen, URL /filescanner/parse als POST - request einem ScanRequestPayload aufrufen.")))
			.build();
	}

	@POST
	@Path("parse")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response scanFile(final ScanRequestPayload payload) {

		if (!authService.isKnown(payload.getClientId())) {

			LOGGER.warn("Unerlaubter Zugriff: clientId = {}", payload.getClientId());

			throw new ClientAuthException();
		}

		ScanResult result = scanService.scanFile(payload);

		return Response.ok(new ResponsePayload(MessagePayload.ok(), result)).build();
	}

}
