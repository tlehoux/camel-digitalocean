package com.viwilo.camel.producer;

import com.myjeeva.digitalocean.common.ActionType;
import com.myjeeva.digitalocean.pojo.*;
import com.viwilo.camel.DigitalOceanConfiguration;
import com.viwilo.camel.constants.DigitalOceanHeaders;
import com.viwilo.camel.DigitalOceanEndpoint;
import com.viwilo.camel.constants.DigitalOceanImageTypes;
import org.apache.camel.Exchange;
import org.apache.camel.util.ObjectHelper;

/**
 * The DigitalOcean producer for Images API.
 */
public class DigitalOceanImagesProducer extends DigitalOceanProducer {

    public DigitalOceanImagesProducer(DigitalOceanEndpoint endpoint, DigitalOceanConfiguration configuration) {
        super(endpoint, configuration);
    }

    public void process(Exchange exchange) throws Exception {

        switch(determineOperation(exchange)) {

            case list:
                getImages(exchange);
                break;
            case ownList:
                getUserImages(exchange);
                break;
            case listActions:
                getImageActions(exchange);
                break;
            case get:
                getImage(exchange);
                break;
            case update:
                updateImage(exchange);
                break;
            case delete:
                deleteImage(exchange);
                break;
            case transfer:
                transferImage(exchange);
                break;
            case convert:
                convertImageToSnapshot(exchange);
                break;
            default:
                throw new IllegalArgumentException("Unsupported operation");
        }
    }


    private void getUserImages(Exchange exchange) throws Exception {
        Images images = getEndpoint().getDigitalOceanClient().getUserImages(configuration.getPage(), configuration.getPerPage());
        LOG.trace("User images : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(), images.getImages());
        exchange.getOut().setBody(images.getImages());
    }


    private void getImages(Exchange exchange) throws Exception {
        DigitalOceanImageTypes type = exchange.getIn().getHeader(DigitalOceanHeaders.TYPE, DigitalOceanImageTypes.class);
        Images images;

        if (ObjectHelper.isNotEmpty(type))
            images = getEndpoint().getDigitalOceanClient().getAvailableImages(configuration.getPage(), configuration.getPerPage(), ActionType.valueOf(type.name()));
        else
            images = getEndpoint().getDigitalOceanClient().getAvailableImages(configuration.getPage(), configuration.getPerPage());
        LOG.trace("All Images : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(), images.getImages());
        exchange.getOut().setBody(images.getImages());
    }

    private void getImage(Exchange exchange) throws Exception {

        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
        String slug = exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_IMAGE, String.class);
        Image image;

        if (ObjectHelper.isNotEmpty(imageId))
            image = getEndpoint().getDigitalOceanClient().getImageInfo(imageId);
        else if (ObjectHelper.isNotEmpty(slug))
            image = getEndpoint().getDigitalOceanClient().getImageInfo(slug);
        else
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " or " +  DigitalOceanHeaders.DROPLET_IMAGE + " must be specified");

        LOG.trace("Image [{}] ", image);
        exchange.getOut().setBody(image);
    }


    private void getImageActions(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId))
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");

        Actions actions = getEndpoint().getDigitalOceanClient().getAvailableImageActions(imageId, configuration.getPage(), configuration.getPerPage());
        LOG.trace("Actions for Image {} : page {} / {} per page [{}] ", imageId, configuration.getPage(), configuration.getPerPage(), actions.getActions());
        exchange.getOut().setBody(actions.getActions());
    }

    private void updateImage(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId))
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");

        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name))
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");

        Image image = new Image();
        image.setId(imageId);
        image.setName(name);
        image = getEndpoint().getDigitalOceanClient().updateImage(image);
        LOG.trace("Update Image {} [{}] ", imageId, image);
        exchange.getOut().setBody(image);
    }

    private void deleteImage(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId))
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");

        Delete delete = getEndpoint().getDigitalOceanClient().deleteImage(imageId);
        LOG.trace("Delete  Image {} [{}] ", imageId, delete);
        exchange.getOut().setBody(delete);
    }

    private void transferImage(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId))
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");

        String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);

        if (ObjectHelper.isEmpty(region))
            throw new IllegalArgumentException(DigitalOceanHeaders.REGION + " must be specified");

        Action action = getEndpoint().getDigitalOceanClient().transferImage(imageId, region);
        LOG.trace("Transfer  Image {} to Region {} [{}] ", imageId, region, action);
        exchange.getOut().setBody(action);
    }

    private void convertImageToSnapshot(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId))
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");

        Action action = getEndpoint().getDigitalOceanClient().convertImage(imageId);
        LOG.trace("Convert Image {} [{}] ", imageId, action);
        exchange.getOut().setBody(action);
    }
}
