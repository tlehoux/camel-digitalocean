package com.viwilo.camel.producer;

import com.myjeeva.digitalocean.pojo.*;
import com.viwilo.camel.DigitalOceanConfiguration;
import com.viwilo.camel.constants.DigitalOceanHeaders;
import com.viwilo.camel.DigitalOceanEndpoint;
import org.apache.camel.Exchange;
import org.apache.camel.util.ObjectHelper;

/**
 * The DigitalOcean producer for Tags API.
 */
public class DigitalOceanTagsProducer extends DigitalOceanProducer {

    public DigitalOceanTagsProducer(DigitalOceanEndpoint endpoint, DigitalOceanConfiguration configuration) {
        super(endpoint, configuration);
    }

    public void process(Exchange exchange) throws Exception {
        switch(determineOperation(exchange)) {

            case list:
                getTags(exchange);
                break;
            case create:
                createTag(exchange);
                break;
            case get:
                getTag(exchange);
                break;
            case delete:
                deleteTag(exchange);
                break;
            case update:
                updateTag(exchange);
                break;
            default:
                throw new IllegalArgumentException("Unsupported operation");
        }

    }

    private void createTag(Exchange exchange) throws Exception {
        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name))
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        Tag tag = getEndpoint().getDigitalOceanClient().createTag(name);
        LOG.trace("Create Tag [{}] ", tag);
        exchange.getOut().setBody(tag);
    }


    private void getTag(Exchange exchange) throws Exception {
        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name))
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        Tag tag = getEndpoint().getDigitalOceanClient().getTag(name);
        LOG.trace("Tag [{}] ", tag);
        exchange.getOut().setBody(tag);
    }

    private void getTags(Exchange exchange) throws Exception {
        Tags tags = getEndpoint().getDigitalOceanClient().getAvailableTags(configuration.getPage(), configuration.getPerPage());
        LOG.trace("All Tags : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(), tags.getTags());
        exchange.getOut().setBody(tags.getTags());
    }

    private void deleteTag(Exchange exchange) throws Exception {
        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name))
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        Delete delete = getEndpoint().getDigitalOceanClient().deleteTag(name);
        LOG.trace("Delete Tag [{}] ", delete);
        exchange.getOut().setBody(delete);
    }

    private void updateTag(Exchange exchange) throws Exception {
        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name))
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");


        String newName = exchange.getIn().getHeader(DigitalOceanHeaders.NEW_NAME, String.class);

        if (ObjectHelper.isEmpty(newName))
            throw new IllegalArgumentException(DigitalOceanHeaders.NEW_NAME + " must be specified");

        Tag tag = getEndpoint().getDigitalOceanClient().updateTag(name, newName);
        LOG.trace("Update Tag [{}] ", tag);
        exchange.getOut().setBody(tag);
    }


}
