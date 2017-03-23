package com.viwilo.camel.producer;

import com.myjeeva.digitalocean.pojo.*;
import com.viwilo.camel.DigitalOceanConfiguration;
import com.viwilo.camel.DigitalOceanEndpoint;
import org.apache.camel.Exchange;

/**
 * The DigitalOcean producer for Sizes API.
 */
public class DigitalOceanSizesProducer extends DigitalOceanProducer {

    public DigitalOceanSizesProducer(DigitalOceanEndpoint endpoint, DigitalOceanConfiguration configuration) {
        super(endpoint, configuration);
    }

    public void process(Exchange exchange) throws Exception {
        Sizes sizes = getEndpoint().getDigitalOceanClient().getAvailableSizes(configuration.getPage());
        LOG.trace("All Sizes : page {} [{}] ", sizes.getSizes(), configuration.getPage());
        exchange.getOut().setBody(sizes.getSizes());
    }

}
