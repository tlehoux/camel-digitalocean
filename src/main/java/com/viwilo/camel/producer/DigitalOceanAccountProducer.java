package com.viwilo.camel.producer;

import com.myjeeva.digitalocean.pojo.*;
import com.viwilo.camel.DigitalOceanConfiguration;
import com.viwilo.camel.DigitalOceanEndpoint;
import org.apache.camel.Exchange;

/**
 * The DigitalOcean producer for Account API.
 */
public class DigitalOceanAccountProducer extends DigitalOceanProducer {

    public DigitalOceanAccountProducer(DigitalOceanEndpoint endpoint, DigitalOceanConfiguration configuration) {
        super(endpoint, configuration);
    }

    public void process(Exchange exchange) throws Exception {
        Account accountInfo = getEndpoint().getDigitalOceanClient().getAccountInfo();
        LOG.trace("Account [{}] ", accountInfo);
        exchange.getOut().setBody(accountInfo);
    }

}
