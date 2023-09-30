// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.rest;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.MessagePayload;
import de.egladil.web.filescanner_api.domain.auth.FilescannerAuthService;
import de.egladil.web.filescanner_api.domain.clamav.ClamAVService;
import de.egladil.web.filescanner_api.domain.error.ClientAuthException;
import de.egladil.web.filescanner_api.domain.scan.ScanRequestPayload;
import de.egladil.web.filescanner_api.domain.scan.ScanResult;
import de.egladil.web.filescanner_api.domain.scan.ScanService;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("files")
@Produces(MediaType.APPLICATION_JSON)
public class FilescannerResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilescannerResource.class);

	@ConfigProperty(name = "quarkus.http.port")
	String port;

	@Inject
	FilescannerAuthService authService;

	@Inject
	ScanService scanService;

	@Inject
	ClamAVService clamAVService;

	@GET
	@Path("hello")
	@Operation(
		operationId = "generateQuizWithUniqueKey",
		summary = "Generiert ein JSON-Objekt mit allen Aufgaben und Lösungen der Rätselgruppe, die durch die fachlichen Parameter eindeutig bestimmt ist. Das ist eine Methode, um auf die Minikänguru-Wettbewerbe zuzugreifen, ohne deren ID zu kennen. Die API liefert nur Quiz mit dem Status FREIGEGEBEN zurück.")
	@APIResponse(
		name = "HelloOKResponse",
		responseCode = "200",
		content = @Content(
			mediaType = "application/json",
			schema = @Schema(implementation = MessagePayload.class)))
	@APIResponse(
		name = "HelloServerError",
		description = "Serverfehler",
		responseCode = "500",
		content = @Content(schema = @Schema(implementation = MessagePayload.class)))
	public Response hello() {

		return Response.ok(MessagePayload.info(
			"Hallo hier spricht die FilescannerResource der filescanner-api vom Port " + port
				+ ". Um eine Datei zu scannen, URL /files/detection als POST - request mit einem ScanRequestPayload aufrufen."))
			.build();
	}

	@Blocking
	@POST
	@Path("detection/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(
		operationId = "scant ein File auf Threads",
		summary = "Es wird auf Viren und Zip-Bomben geprüft")
	@APIResponse(
		name = "ScanFileOKResponse",
		description = "Scan fertig",
		responseCode = "200",
		content = @Content(
			mediaType = "application/json",
			schema = @Schema(implementation = ScanResult.class)))
	@APIResponse(
		name = "ScanFileNotAuthorized",
		description = "wenn mit einer unbekannten Client-ID aufgerufen wurde",
		responseCode = "401")
	@APIResponse(
		name = "ScanFileBadRequest",
		description = "wenn der 'X-CLIENT-ID'- Header fehlt",
		responseCode = "401")
	@APIResponse(
		name = "PongServerError",
		description = "Serverfehler",
		responseCode = "500",
		content = @Content(schema = @Schema(implementation = MessagePayload.class)))
	public Response scanFile(@Valid final ScanRequestPayload payload) {

		if (!authService.isKnown(payload.getClientId())) {

			LOGGER.warn("Unerlaubter Zugriff: clientId = {}", payload.getClientId());

			throw new ClientAuthException();
		}

		ScanResult result = scanService.scanFile(payload);

		return Response.ok(result).build();
	}

	@GET
	@Path("ping/v1")
	@Operation(
		operationId = "prüft, ob ClamAV erreichbar ist",
		summary = "Es wird auf Viren und Zip-Bomben geprüft")
	@APIResponse(
		name = "PongOKResponse",
		description = "Quiz erfolgreich geladen",
		responseCode = "200")
	@APIResponse(
		name = "PongNotAuthorized",
		description = "wenn mit einer unbekannten Client-ID aufgerufen wurde",
		responseCode = "401")
	@APIResponse(
		name = "PongServerError",
		description = "Serverfehler",
		responseCode = "500",
		content = @Content(schema = @Schema(implementation = MessagePayload.class)))
	public Response pong(@HeaderParam(value = "X-CLIENT-ID") @NotNull final String clientId) {

		if (!authService.isKnown(clientId)) {

			LOGGER.warn("Unerlaubter Zugriff: clientId = {}", clientId);

			throw new ClientAuthException();
		}

		boolean isAlive = clamAVService.checkAlive();

		return isAlive ? Response.ok().build() : Response.status(503).build();

	}

}
