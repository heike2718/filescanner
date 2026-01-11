//=====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
//=====================================================
package de.egladil.web.filescanner_api.infrastructure.error;

import de.egladil.web.filescanner_api.domain.MessagePayload;
import de.egladil.web.filescanner_api.domain.error.UnsupportedVersionException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 */
@Provider
public class UnsupportedVersionExceptionMapper implements ExceptionMapper<UnsupportedVersionException> {

	@Override
	public Response toResponse(UnsupportedVersionException exception) {

		MessagePayload messagePayload = MessagePayload.error(exception.getMessage());

		return Response
            .status(Response.Status.NOT_ACCEPTABLE)
            .entity(messagePayload)
            .build();
	}
}
