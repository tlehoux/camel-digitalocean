package com.viwilo.camel.producer;

import com.myjeeva.digitalocean.pojo.*;
import com.viwilo.camel.DigitalOceanConfiguration;
import com.viwilo.camel.DigitalOceanEndpoint;
import org.apache.camel.Exchange;

/**
 * The DigitalOcean producer for Regions API.
 */
public class DigitalOceanRegionsProducer extends DigitalOceanProducer {

    public DigitalOceanRegionsProducer(DigitalOceanEndpoint endpoint, DigitalOceanConfiguration configuration) {
        super(endpoint, configuration);
    }

    public void process(Exchange exchange) throws Exception {
        Regions regions = getEndpoint().getDigitalOceanClient().getAvailableRegions(configuration.getPage());
        LOG.trace("All Regions : page {} [{}] ", regions.getRegions(), configuration.getPage());
        exchange.getOut().setBody(regions.getRegions());
    }

}
