// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.domain.events;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ZipBombDetected
 */
public class ZipBombDetected extends AbstractFilescannerDomainEvent {

	@JsonProperty
	private String clientId;

	@JsonProperty
	private String ownerId;

	@JsonProperty
	private String fileName;

	@JsonProperty
	private long compressionRatio;

	@Override
	@JsonIgnore
	public String typeName() {

		return FilescannerEventType.ZIP_BOMB_DETECTED.getLabel();
	}

	@Override
	@JsonIgnore
	public String getMessagingPreview() {

		return "ZipBombDetected [clientId=" + StringUtils.abbreviate(clientId, 11) + ", ownerId="
			+ StringUtils.abbreviate(ownerId, 11) + ", fileName=" + fileName + ", compressionRatio="
			+ compressionRatio + "]";
	}

	public String getOwnerId() {

		return ownerId;
	}

	public ZipBombDetected withOwnerId(final String ownerId) {

		this.ownerId = ownerId;
		return this;
	}

	public String getFileName() {

		return fileName;
	}

	public ZipBombDetected withFileName(final String fileName) {

		this.fileName = fileName;
		return this;
	}

	public long getCompressionRatio() {

		return compressionRatio;
	}

	public ZipBombDetected withCompressionRatio(final long compressionRatio) {

		this.compressionRatio = compressionRatio;
		return this;
	}

	public String getClientId() {

		return clientId;
	}

	public ZipBombDetected withClientId(final String clientId) {

		this.clientId = StringUtils.abbreviate(clientId, 11);
		return this;
	}

}
