package com.sample.springbootappwithmetrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import static io.micrometer.core.instrument.Tag.of;
import static java.util.Arrays.stream;

@Configuration
public class Config implements MeterRegistryCustomizer<MeterRegistry> {

    /**
     * Assigned at start unique identifier
     */
    static final UUID instanceId = UUID.randomUUID();
    static final String UNKNOWN = "unknown";
    static final String INSTANCE_ID = "instanceId";
    static final String HOST = "host";
    static final String APP = "app";
    static final String ENV = "env";

    private final Environment env;
    private final String app;

    Config(final Environment env,
                  @Value("${spring.application.name:my-service}") final String app) {
        this.env = env;
        this.app = app;
    }

    @Override
    public void customize(final MeterRegistry registry) {
        //registry.config().commonTags(commonTags());
    }

    public Tags commonTags() {

        return Tags.of(
                getEnvTag(),
                getAppTag(),
                getInstanceIdTag(),
                getHostTag()
        );
    }

    private Tag getHostTag() {
        try {
            final String hostname = InetAddress.getLocalHost().getHostName();
            return of(HOST, hostname);
        } catch (final UnknownHostException e) {
           // log.warn("Retrieval of hostname failed, fallback to unique identifier.");
            return of(HOST, instanceId.toString());
        }
    }

    private Tag getInstanceIdTag() {
        return of(INSTANCE_ID, instanceId.toString());
    }

    private Tag getAppTag() {
        return of(APP, app);
    }

    private Tag getEnvTag() {
        return of(ENV, stream(env.getActiveProfiles()).findFirst().orElse(UNKNOWN));
    }
}
