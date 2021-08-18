// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.egladil.web.commons_validation.payload.MessagePayload;
import de.egladil.web.commons_validation.payload.ResponsePayload;
import de.egladil.web.filescanner_api.domain.error.ClientAuthException;
import de.egladil.web.filescanner_service.error.FilescannerRuntimeException;

/**
 * FilescannerExceptionMapper
 */
@Provider
public class FilescannerExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilescannerExceptionMapper.class);

	@Override
	public Response toResponse(final Throwable exception) {

		if (exception instanceof ClientAuthException) {

			return Response.status(401).entity(ResponsePayload.messageOnly(MessagePayload.error("keine Berechtigung"))).build();
		}

		if (exception instanceof FilescannerRuntimeException) {

			return createInternalServerErrorResponse();
		}

		LOGGER.error(exception.getMessage(), exception);

		return createInternalServerErrorResponse();
	}

	/**
	 * @return
	 */
	private Response createInternalServerErrorResponse() {

		ResponsePayload payload = ResponsePayload
			.messageOnly(MessagePayload.error("internalServerError"));

		return Response.status(500).entity(serializeAsJson(payload)).build();
	}

	private String serializeAsJson(final ResponsePayload rp) {

		try {

			return new ObjectMapper().writeValueAsString(rp);
		} catch (JsonProcessingException e) {

			MessagePayload mp = rp.getMessage();
			return mp.getLevel() + " " + mp.getMessage();

		}
	}

}
