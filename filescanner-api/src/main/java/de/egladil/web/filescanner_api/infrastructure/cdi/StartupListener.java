// =====================================================
// Project: filescanner-api
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.filescanner_api.infrastructure.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;

/**
 * StartupListener
 */
@ApplicationScoped
public class StartupListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

    @ConfigProperty(name = "quarkus.http.root-path")
    String quarkusRootPath;

    @ConfigProperty(name = "quarkus.http.port")
    String port;

    @ConfigProperty(name = "clamav.host")
    String clamAVHost;

    @ConfigProperty(name = "clamav.port")
    String clamAVPort;

    @ConfigProperty(name = "quarkus.mailer.mock")
    String mockTheMailer;

    @ConfigProperty(name = "quarkus.application.version")
    String version;

    void onStartup(@Observes final StartupEvent ev) {

        LOGGER
                .warn(" ===========> Version {} of the application is starting with profiles {}", version,
                        StringUtils.join(ConfigUtils.getProfiles()));

        LOGGER.warn(" ===========>  ClamAV: host={},port={}", clamAVHost, clamAVPort);
        LOGGER.warn(" ===========>  mockTheMailer={}", mockTheMailer);
        LOGGER.warn(" ===========>  quarkusRootPath={}", quarkusRootPath);
        LOGGER.warn(" ===========>  port={}", port);
    }
}
