package org.fuin.cqrs4j.example.javasecdi.qry.app;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.fuin.cqrs4j.example.javasecdi.shared.app.SharedConfig;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.http.HttpClient;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * CDI factory that creates a {@link HttpClient} instance.
 */
@ApplicationScoped
public class HttpClientFactory {

    @Produces
    public HttpClient getHttpClient(final SharedConfig config) {
        return HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.getEventStoreUser(), config.getEventStorePassword().toCharArray());
                    }
                })
                .connectTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
    }

}
