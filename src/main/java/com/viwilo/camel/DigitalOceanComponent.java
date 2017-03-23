package com.viwilo.camel;

import java.util.Map;

import com.viwilo.camel.constants.DigitalOceanResources;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;

import org.apache.camel.impl.UriEndpointComponent;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the component that manages {@link DigitalOceanEndpoint}.
 */
public class DigitalOceanComponent extends UriEndpointComponent {

    private static final transient Logger LOG = LoggerFactory.getLogger(DigitalOceanComponent.class);


    public DigitalOceanComponent() {
        super(DigitalOceanEndpoint.class);
    }

    public DigitalOceanComponent(CamelContext context) {
        super(context, DigitalOceanEndpoint.class);
    }

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {

        DigitalOceanConfiguration configuration = new DigitalOceanConfiguration();
        setProperties(configuration, parameters);
        configuration.setResource(DigitalOceanResources.valueOf(remaining));

        if (ObjectHelper.isEmpty(configuration.getOAuthToken()) && ObjectHelper.isEmpty(configuration.getDigitalOceanClient()))
            throw new DigitalOceanException("oAuthToken or digitalOceanClient must be specified");

        Endpoint endpoint = new DigitalOceanEndpoint(uri, this, configuration);
        return endpoint;
    }

}
