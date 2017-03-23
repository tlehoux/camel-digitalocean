package com.viwilo.camel;

import com.myjeeva.digitalocean.impl.DigitalOceanClient;
import com.viwilo.camel.producer.*;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a DigitalOcean endpoint.
 */
@UriEndpoint(scheme = "digitalocean", title = "DigitalOcean", syntax="digitalocean:label", producerOnly = true, label = "cloud,management")
public class DigitalOceanEndpoint extends DefaultEndpoint {

    private static final transient Logger LOG = LoggerFactory.getLogger(DigitalOceanEndpoint.class);

    @UriParam
    private DigitalOceanConfiguration configuration;

    private DigitalOceanClient digitalOceanClient;

    public DigitalOceanEndpoint(String uri, DigitalOceanComponent component, DigitalOceanConfiguration configuration) {
        super(uri, component);
        this.configuration = configuration;
    }

    public Producer createProducer() throws Exception {
        LOG.trace("Resolve producer digitalocean endpoint {" + configuration.getResource() + "}");

        switch (configuration.getResource()) {
            case account:
                return new DigitalOceanAccountProducer(this, configuration);
            case actions:
                return new DigitalOceanActionsProducer(this, configuration);
            case blockStorages:
                return new DigitalOceanBlockStoragesProducer(this, configuration);
            case droplets:
                return new DigitalOceanDropletsProducer(this, configuration);
            case images:
                return new DigitalOceanImagesProducer(this, configuration);
            case snapshots:
                return new DigitalOceanSnapshotsProducer(this, configuration);
            case keys:
                return new DigitalOceanKeysProducer(this, configuration);
            case regions:
                return new DigitalOceanRegionsProducer(this, configuration);
            case sizes:
                return new DigitalOceanSizesProducer(this, configuration);
            case floatingIPs:
                return new DigitalOceanFloatingIPsProducer(this, configuration);
            case tags:
                return new DigitalOceanTagsProducer(this, configuration);
            default:
                throw new UnsupportedOperationException("Operation specified is not valid for producer");
        }

    }

    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException("You cannot receive messages from this endpoint");
    }

    public boolean isSingleton() {
        return true;
    }


    @Override
    public void doStart() throws Exception {
        super.doStart();

        if(configuration.getDigitalOceanClient() != null)
            digitalOceanClient = configuration.getDigitalOceanClient();
        else
            if(configuration.getHttpProxyHost() != null && configuration.getHttpProxyPort() != null) {

                 HttpClientBuilder builder = HttpClients.custom()
                        .useSystemProperties()
                        .setProxy(new HttpHost(configuration.getHttpProxyHost(), configuration.getHttpProxyPort()));

                if(configuration.getHttpProxyUser() != null && configuration.getHttpProxyPassword() != null) {
                    BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
                    credsProvider.setCredentials(
                            new AuthScope(configuration.getHttpProxyHost(), configuration.getHttpProxyPort()),
                            new UsernamePasswordCredentials(configuration.getHttpProxyUser() , configuration.getHttpProxyPassword()));
                    builder.setDefaultCredentialsProvider(credsProvider);

                }

                digitalOceanClient =  new DigitalOceanClient("v2", configuration.getOAuthToken(), builder.build());

            } else
                digitalOceanClient =  new DigitalOceanClient(configuration.getOAuthToken());


    }

    public DigitalOceanConfiguration getConfiguration() { return configuration; }
    public DigitalOceanClient getDigitalOceanClient() {
        return digitalOceanClient;
    }

}
