package com.viwilo.camel.producer;

import com.myjeeva.digitalocean.pojo.*;
import com.viwilo.camel.DigitalOceanConfiguration;
import com.viwilo.camel.DigitalOceanEndpoint;
import com.viwilo.camel.constants.DigitalOceanHeaders;
import org.apache.camel.Exchange;
import org.apache.camel.util.ObjectHelper;

/**
 * The DigitalOcean producer for Actions API.
 */
public class DigitalOceanActionsProducer extends DigitalOceanProducer {

    public DigitalOceanActionsProducer(DigitalOceanEndpoint endpoint, DigitalOceanConfiguration configuration) {
        super(endpoint, configuration);
    }

    public void process(Exchange exchange) throws Exception {
        switch(determineOperation(exchange)) {

            case list:
                getActions(exchange);
                break;
            case get:
                getAction(exchange);
                break;
            default:
                throw new IllegalArgumentException("Unsupported operation");
        }

    }


    private void getAction(Exchange exchange) throws Exception {
        Integer actionId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(actionId))
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        Action action = getEndpoint().getDigitalOceanClient().getActionInfo(actionId);
        LOG.trace("Action [{}] ", action);
        exchange.getOut().setBody(action);
    }

    private void getActions(Exchange exchange) throws Exception {
        Actions actions = getEndpoint().getDigitalOceanClient().getAvailableActions(configuration.getPage(), configuration.getPerPage());
        LOG.trace("All Actions : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(), actions.getActions());
        exchange.getOut().setBody(actions.getActions());
    }
}
