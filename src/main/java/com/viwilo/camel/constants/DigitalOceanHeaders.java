package com.viwilo.camel.constants;

/**
 * Created by thomas on 21/03/2017.
 */
public interface DigitalOceanHeaders {

        String OPERATION                            = "CamelDigitalOceanOperation";
        String ID                                   = "CamelDigitalOceanId";
        String TYPE                                 = "CamelDigitalOceanType";
        String NAME                                 = "CamelDigitalOceanName";
        String NEW_NAME                             = "CamelDigitalOceanNewName";
        String NAMES                                = "CamelDigitalOceanNames";
        String REGION                               = "CamelDigitalOceanRegion";
        String DESCRIPTION                          = "CamelDigitalOceanDescription";

        String DROPLET_SIZE                         = "CamelDigitalOceanDropletSize";
        String DROPLET_IMAGE                        = "CamelDigitalOceanDropletImage";
        String DROPLET_KEYS                         = "CamelDigitalOceanDropletSSHKeys";
        String DROPLET_ENABLE_BACKUPS               = "CamelDigitalOceanDropletEnableBackups";
        String DROPLET_ENABLE_IPV6                  = "CamelDigitalOceanDropletEnableIpv6";
        String DROPLET_USER_DATA                    = "CamelDigitalOceanDropletUserData";
        String DROPLET_VOLUMES                      = "CamelDigitalOceanDropletVolumes";
        String DROPLET_TAGS                         = "CamelDigitalOceanDropletTags";

        String DROPLET_ID                           = "CamelDigitalOceanDropletId";
        String IMAGE_ID                             = "CamelDigitalOceanImageId";
        String KERNEL_ID                            = "CamelDigitalOceanKernelId";
        String VOLUME_NAME                          = "CamelDigitalOceanVolumeName";
        String VOLUME_SIZE_GIGABYTES                = "CamelDigitalOceanVolumeSizeGigabytes";

        String FLOATING_IP_ADDRESS                  = "CamelDigitalOceanFloatingIPAddress";

        String KEY_FINGERPRINT                      = "CamelDigitalOceanKeyFingerprint";
        String KEY_PUBLIC_KEY                       = "CamelDigitalOceanKeyPublicKey";




}
