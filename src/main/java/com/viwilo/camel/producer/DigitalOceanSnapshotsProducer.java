package com.viwilo.camel.producer;

import com.myjeeva.digitalocean.pojo.Delete;
import com.myjeeva.digitalocean.pojo.Snapshot;
import com.myjeeva.digitalocean.pojo.Snapshots;
import com.viwilo.camel.DigitalOceanConfiguration;
import com.viwilo.camel.DigitalOceanEndpoint;
import com.viwilo.camel.constants.DigitalOceanHeaders;
import com.viwilo.camel.constants.DigitalOceanSnapshotTypes;
import org.apache.camel.Exchange;
import org.apache.camel.util.ObjectHelper;

/**
 * The DigitalOcean producer for Snapshots API.
 */
public class DigitalOceanSnapshotsProducer extends DigitalOceanProducer {

    public DigitalOceanSnapshotsProducer(DigitalOceanEndpoint endpoint, DigitalOceanConfiguration configuration) {
        super(endpoint, configuration);
    }

    public void process(Exchange exchange) throws Exception {

        switch (determineOperation(exchange)) {

            case list:
                getSnapshots(exchange);
                break;
            case get:
                getSnapshot(exchange);
                break;
            case delete:
                deleteSnapshot(exchange);
                break;

            default:
                throw new IllegalArgumentException("Unsupported operation");
        }


    }


    private void getSnapshots(Exchange exchange) throws Exception {
        DigitalOceanSnapshotTypes type = exchange.getIn().getHeader(DigitalOceanHeaders.TYPE, DigitalOceanSnapshotTypes.class);
        Snapshots snapshots;

        if (ObjectHelper.isNotEmpty(type))
            if(type == DigitalOceanSnapshotTypes.droplet)
                snapshots = getEndpoint().getDigitalOceanClient().getAllDropletSnapshots(configuration.getPage(), configuration.getPerPage());
            else
                snapshots = getEndpoint().getDigitalOceanClient().getAllVolumeSnapshots(configuration.getPage(), configuration.getPerPage());
        else
            snapshots = getEndpoint().getDigitalOceanClient().getAvailableSnapshots(configuration.getPage(), configuration.getPerPage());

        LOG.trace("All Snapshots : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(), snapshots.getSnapshots());
        exchange.getOut().setBody(snapshots.getSnapshots());
    }

    private void getSnapshot(Exchange exchange) throws Exception {
        String snapshotId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);

        if (ObjectHelper.isEmpty(snapshotId))
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");

        Snapshot snapshot = getEndpoint().getDigitalOceanClient().getSnaphotInfo(snapshotId);
        LOG.trace("Snapshot [{}] ", snapshot);
        exchange.getOut().setBody(snapshot);

    }

    private void deleteSnapshot(Exchange exchange) throws Exception {
        String snapshotId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);

        if (ObjectHelper.isEmpty(snapshotId))
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");

        Delete delete = getEndpoint().getDigitalOceanClient().deleteSnapshot(snapshotId);
        LOG.trace("Delete Snapshot [{}] ", delete);
        exchange.getOut().setBody(delete);

    }


}
