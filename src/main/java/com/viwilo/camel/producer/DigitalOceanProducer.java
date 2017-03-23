package com.viwilo.camel.producer;

import com.viwilo.camel.DigitalOceanConfiguration;
import com.viwilo.camel.constants.DigitalOceanHeaders;
import com.viwilo.camel.DigitalOceanEndpoint;
import com.viwilo.camel.constants.DigitalOceanOperations;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DigitalOcean producer.
 */
public abstract class DigitalOceanProducer extends DefaultProducer {
    protected static final Logger LOG = LoggerFactory.getLogger(DigitalOceanProducer.class);

    private DigitalOceanEndpoint endpoint;
    protected DigitalOceanConfiguration configuration;

    public DigitalOceanProducer(DigitalOceanEndpoint endpoint, DigitalOceanConfiguration configuration) {
        super(endpoint);
        this.endpoint = endpoint;
        this.configuration = configuration;
    }

    protected DigitalOceanOperations determineOperation(Exchange exchange) {
        DigitalOceanOperations operation = exchange.getIn().getHeader(DigitalOceanHeaders.OPERATION, DigitalOceanOperations.class);
        return ObjectHelper.isNotEmpty(operation) ? operation : configuration.getOperation();
    }

    /*protected Integer determineId(Exchange exchange) {
        Integer id = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
        return ObjectHelper.isNotEmpty(id) ? id : configuration.getId();
    }*/

   /* protected Integer determinePage(Exchange exchange) {
        Integer page = exchange.getIn().getHeader(DigitalOceanHeaders.PAGE, Integer.class);
        return ObjectHelper.isNotEmpty(page) ? page : configuration.getPage();
    }

    protected Integer determinePerPage(Exchange exchange) {
        Integer perPage = exchange.getIn().getHeader(DigitalOceanHeaders.PER_PAGE, Integer.class);
        return ObjectHelper.isNotEmpty(perPage) ? perPage : configuration.getPage();
    }*/

    @Override
    public DigitalOceanEndpoint getEndpoint() {
        return endpoint;
    }
}
