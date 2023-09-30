// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.filescanner_api.domain.MessagePayload;
import de.egladil.web.filescanner_api.domain.error.ClientAuthException;
import de.egladil.web.filescanner_api.domain.error.FilescannerRuntimeException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * FilescannerExceptionMapper
 */
@Provider
public class FilescannerExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilescannerExceptionMapper.class);

	@Override
	public Response toResponse(final Throwable exception) {

		if (exception instanceof ClientAuthException) {

			return Response.status(401).entity(
				MessagePayload.error("keine Berechtigung")).build();
		}

		if (exception instanceof FilescannerRuntimeException) {

			return Response.serverError().entity(MessagePayload.error("internalServerError")).build();
		}

		LOGGER.error(exception.getMessage(), exception);

		return Response.serverError().entity(MessagePayload.error("internalServerError")).build();
	}

}
