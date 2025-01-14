package org.fuin.cqrs4j.example.javasecdi.qry.app;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import org.fuin.cqrs4j.example.javasecdi.shared.app.SharedConfig;
import org.fuin.esc.admin.HttpProjectionAdminEventStore;
import org.fuin.esc.api.ProjectionAdminEventStore;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;

/**
 * CDI factory that creates a {@link ProjectionAdminEventStore} instance.
 */
@ApplicationScoped
public class QryProjectionAdminEventStoreFactory {

    @Produces
    @ApplicationScoped
    public ProjectionAdminEventStore getProjectionAdminEventStore(final SharedConfig config, final HttpClient httpClient) {
        final String url = "http://" + config.getEventStoreHost() + ":" + config.getEventStoreHttpPort();
        try {
            final ProjectionAdminEventStore es = new HttpProjectionAdminEventStore(httpClient, new URL(url));
            es.open();
            return es;
        } catch (final MalformedURLException ex) {
            throw new RuntimeException("Failed to create URL: " + url, ex);
        }
    }

    /**
     * Closes the projection admin event store when the context is disposed.
     *
     * @param es Event store to close.
     */
    public void closeProjectionAdminEventStore(@Disposes final ProjectionAdminEventStore es) {
        es.close();
    }

}
