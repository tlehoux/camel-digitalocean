# Camel DigitalOcean Component

The **DigitalOcean** component allows you to manage Droplets and resources within the DigitalOcean cloud with **Camel** by encapsulating [digitalocean-api-java](https://www.digitalocean.com/community/projects/api-client-in-java). All of the functionality that you are familiar with in the DigitalOcean control panel is also available through this Camel component.


## Prerequisites

You must have a valid DigitalOcean account and a valid OAuth token. You can generate an OAuth token by visiting the [Apps & API](https://cloud.digitalocean.com/settings/applications) section of the DigitalOcean control panel for your account.

## URI format

The **DigitalOcean Component** uses the following URI format:

```
digitalocean://endpoint?[options]
```
where `endpoint` is a DigitalOcean resource type.

Example : to list your droplets:

```
digitalocean://droplets?operation=list&oAuthToken=XXXXXX&page=1&perPage=10
```

The DigitalOcean component only supports producer endpoints so you cannot use this component at the beginning of a route to listen to messages in a channel.

## URI Endpoints

There is one endpoint for each DigitalOcean resources (defined in `DigitalOceanResources` enumeration):

| Endpoint | Description |
| ------ | ----------- |
| `account` | Your account on DigitalOcean. |
| `actions` | Actions are records of events that have occurred on the resources in your account.  |
| `blockStorages` | Block Storage volumes provide expanded storage capacity for your Droplets and can be moved between Droplets within a specific region. |
| `droplets` | A Droplet is a DigitalOcean virtual machine. |
| `images` | An image may refer to a snapshot that has been taken of a Droplet instance. It may also mean an image representing an automatic backup of a Droplet. |
| `snapshots` | Snapshots are saved instances of a Droplet or a volume. |
| `keys` | DigitalOcean allows you to add SSH public keys to the interface so that you can embed your public key into a Droplet at the time of creation. |
| `regions` | A region in DigitalOcean represents a datacenter where Droplets can be deployed and images can be transferred. |
| `sizes` | The sizes objects represent different packages of hardware resources that can be used for Droplets.  |
| `floatingIPs` | Floating IP objects represent a publicly-accessible static IP addresses that can be mapped to one of your Droplets. |
| `tags` | A Tag is a label that can be applied to a resource (currently only Droplets) in order to better organize or facilitate the lookups and actions on it. |


## URI Options

You have to provide the `digitalOceanClient` in the Registry or your `oAuthToken` to access the DigitalOcean service.

| Option | Type | Example | Description |
| ------ | ---- | ------- | ----------- |
| `operation` | String | `operation=list` | The operation to perform to the resource. It can also be set with the  `CamelDigitalOceanOperation` Message header. |
| `oAuthToken` | String |  | Your DigitalOcean oAuth token. |
| `digitalOceanClient` | `com.myjeeva.digitalocean.DigitalOcean` | Reference to a `com.myjeeva.digitalocean.DigitalOcean` in the Registry. |
| `page` | Integer | `page=3` | Can be used for `list` operations, for pagination. Force the page number.|
| `perPage` | Integer | `perPage=50` | Can be used for `list` operations, for pagiantion. Set the number of item per request. |


You have to provide an **operation** value for each endpoint, with the `operation` URI option or the `CamelDigitalOceanOperation` message header.

All **operation** values are defined in `DigitalOceanOperations` enumeration.

All **header** names used by the component are defined in `DigitalOceanHeaders` enumeration.


## Message body result
All message bodies returned are using objects provided by the **digitalocean-api-java** library.


## API Rate Limits


DigitalOcean REST API encapsulated by camel-digitalocean component is subjected to API Rate Limiting. You can find the per method limits in the [API Rate Limits documentation](https://developers.digitalocean.com/documentation/v2/#rate-limit).


## Account endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `get` | get account info |  | `com.myjeeva.digitalocean.pojo.Account`  |



## BlockStorages endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` | list all of the Block Storage volumes available on your account |  | `List<com.myjeeva.digitalocean.pojo.Volume>`  |
| `get` | show information about a Block Storage volume| `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Volume`  |
| `get` | show information about a Block Storage volume by name| `CamelDigitalOceanName` _String_ <br>`CamelDigitalOceanRegion` _String_| `com.myjeeva.digitalocean.pojo.Volume`  |
| `listSnapshots` | retrieve the snapshots that have been created from a volume | `CamelDigitalOceanId` _Integer_| `List<com.myjeeva.digitalocean.pojo.Snapshot>`  |
| `create` | create a new volume | `CamelDigitalOceanVolumeSizeGigabytes`  _Integer_<br>`CamelDigitalOceanName` _String_<br>`CamelDigitalOceanDescription`* _String_<br>`CamelDigitalOceanRegion`* _String_| `com.myjeeva.digitalocean.pojo.Volume`  |
| `delete` | delete a Block Storage volume, destroying all data and removing it from your account| `CamelDigitalOceanId`  _Integer_| `com.myjeeva.digitalocean.pojo.Delete`|
| `delete` | delete a Block Storage volume by name| `CamelDigitalOceanName` _String_<br>`CamelDigitalOceanRegion` _String_| `com.myjeeva.digitalocean.pojo.Delete`
| `attach` | attach a Block Storage volume to a Droplet| `CamelDigitalOceanId`  _Integer_ <br>`CamelDigitalOceanDropletId`  _Integer_<br>`CamelDigitalOceanDropletRegion` _String_| `com.myjeeva.digitalocean.pojo.Action`
| `attach` | attach a Block Storage volume to a Droplet by name| `CamelDigitalOceanName` _String_<br>`CamelDigitalOceanDropletId`  _Integer_<br>`CamelDigitalOceanDropletRegion` _String_| `com.myjeeva.digitalocean.pojo.Action`
| `detach` | detach a Block Storage volume from a Droplet| `CamelDigitalOceanId`  _Integer_ <br>`CamelDigitalOceanDropletId`  _Integer_<br>`CamelDigitalOceanDropletRegion` _String_| `com.myjeeva.digitalocean.pojo.Action`
| `attach` | detach a Block Storage volume from a Droplet by name| `CamelDigitalOceanName` _String_<br>`CamelDigitalOceanDropletId`  _Integer_<br>`CamelDigitalOceanDropletRegion` _String_| `com.myjeeva.digitalocean.pojo.Action`
| `resize` | resize a Block Storage volume | `CamelDigitalOceanVolumeSizeGigabytes`  _Integer_<br>`CamelDigitalOceanRegion` _String_| `com.myjeeva.digitalocean.pojo.Action`  |
| `listActions` | retrieve all actions that have been executed on a volume | `CamelDigitalOceanId`  _Integer_| `List<com.myjeeva.digitalocean.pojo.Action>`  |

## Droplets endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` | list all Droplets in your account |  | `List<com.myjeeva.digitalocean.pojo.Droplet>`  |
| `get` | show an individual droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Droplet`  |
| `create` | create a new Droplet | `CamelDigitalOceanName` _String_ <br>`CamelDigitalOceanDropletImage` _String_ <br>`CamelDigitalOceanRegion` _String_ <br>`CamelDigitalOceanDropletSize` _String_ <br>`CamelDigitalOceanDropletSSHKeys`* _List\<String\>_ <br>`CamelDigitalOceanDropletEnableBackups`* _Boolean_ <br>`CamelDigitalOceanDropletEnableIpv6`* _Boolean_ <br>`CamelDigitalOceanDropletEnablePrivateNetworking`* _Boolean_ <br>`CamelDigitalOceanDropletUserData`* _String_ <br>`CamelDigitalOceanDropletVolumes`* _List\<String\>_ <br>`CamelDigitalOceanDropletTags` _List\<String\>_| `com.myjeeva.digitalocean.pojo.Droplet`  |
| `create` | create multiple Droplets | `CamelDigitalOceanNames` _List\<String\>_ <br>`CamelDigitalOceanDropletImage` _String_ <br>`CamelDigitalOceanRegion` _String_ <br>`CamelDigitalOceanDropletSize` _String_ <br>`CamelDigitalOceanDropletSSHKeys`* _List\<String\>_ <br>`CamelDigitalOceanDropletEnableBackups`* _Boolean_ <br>`CamelDigitalOceanDropletEnableIpv6`* _Boolean_ <br>`CamelDigitalOceanDropletEnablePrivateNetworking`* _Boolean_ <br>`CamelDigitalOceanDropletUserData`* _String_ <br>`CamelDigitalOceanDropletVolumes`* _List\<String\>_ <br>`CamelDigitalOceanDropletTags` _List\<String\>_| `com.myjeeva.digitalocean.pojo.Droplet`  |
| `delete` | delete a Droplet, | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Delete`  |
| `enableBackups` | enable backups on an existing Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `disableBackups` | disable backups on an existing Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `enableIpv6` | enable IPv6 networking on an existing Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `enablePrivateNetworking` | enable private networking on an existing Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `reboot` | reboot a Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `powerCycle` | power cycle a Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `shutdown` | shutdown a Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `powerOff` | power off a Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `powerOn` | power on a Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `restore` | shutdown a Droplet | `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanImageId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `passwordReset` | reset the password for a Droplet | `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `resize` | resize a Droplet | `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanDropletSize` _String_| `com.myjeeva.digitalocean.pojo.Action`  |
| `rebuild` | rebuild a Droplet | `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanImageId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `rename` | rename a Droplet | `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanName` _String_| `com.myjeeva.digitalocean.pojo.Action`  |
| `changeKernel` | change the kernel of a Droplet | `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanKernelId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `takeSnapshot` | snapshot a Droplet | `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanName`* _String_| `com.myjeeva.digitalocean.pojo.Action`  |
| `tag` | tag a Droplet | `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanName` _String_| `com.myjeeva.digitalocean.pojo.Response`  |
| `untag` | untag a Droplet | `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanName` _String_| `com.myjeeva.digitalocean.pojo.Response`  |
| `listKernels` | retrieve a list of all kernels available to a Droplet | `CamelDigitalOceanId` _Integer_ | `List<com.myjeeva.digitalocean.pojo.Kernel>`  |
| `listSnapshots` | retrieve the snapshots that have been created from a Droplet | `CamelDigitalOceanId` _Integer_ | `List<com.myjeeva.digitalocean.pojo.Snapshot>`  |
| `listBackups` |  retrieve any backups associated with a Droplet | `CamelDigitalOceanId` _Integer_ | `List<com.myjeeva.digitalocean.pojo.Backup>`  |
| `listActions` |  retrieve all actions that have been executed on a Droplet | `CamelDigitalOceanId` _Integer_ | `List<com.myjeeva.digitalocean.pojo.Action>`  |
| `listNeighbors` |  retrieve a list of droplets that are running on the same physical server | `CamelDigitalOceanId` _Integer_ | `List<com.myjeeva.digitalocean.pojo.Droplet>`  |
| `listAllNeighbors` |  retrieve a list of any droplets that are running on the same physical hardware | | `List<com.myjeeva.digitalocean.pojo.Droplet>`  |

## Images endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` | list images available on your account | `CamelDigitalOceanType`* _DigitalOceanImageTypes_ | `List<com.myjeeva.digitalocean.pojo.Image>`  |
| `ownList` | retrieve only the private images of a user | | `List<com.myjeeva.digitalocean.pojo.Image>`  |
| `listActions` |  retrieve all actions that have been executed on a Image | `CamelDigitalOceanId` _Integer_ | `List<com.myjeeva.digitalocean.pojo.Action>`  |
| `get` | retrieve information about an image (public or private) by id| `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Image`  |
| `get` | retrieve information about an public image by slug| `CamelDigitalOceanDropletImage` _String_| `com.myjeeva.digitalocean.pojo.Image`  |
| `update` | update an image| `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanName` _String_| `com.myjeeva.digitalocean.pojo.Image`  |
| `delete` | delete an image| `CamelDigitalOceanId` _Integer_ | `com.myjeeva.digitalocean.pojo.Delete`  |
| `transfer` | transfer an image to another region| `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanRegion` _String_| `com.myjeeva.digitalocean.pojo.Action`  |
| `convert` | convert an image, for example, a backup to a snapshot| `CamelDigitalOceanId` _Integer_ | `com.myjeeva.digitalocean.pojo.Action`  |

## Snapshots endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` | list all of the snapshots available on your account | `CamelDigitalOceanType`* _DigitalOceanSnapshotTypes_ | `List<com.myjeeva.digitalocean.pojo.Snapshot>`  |
| `get` | retrieve information about a snapshot| `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Snapshot`  |
| `delete` | delete an snapshot| `CamelDigitalOceanId` _Integer_ | `com.myjeeva.digitalocean.pojo.Delete`  |


## Keys endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` |  list all of the keys in your account | | `List<com.myjeeva.digitalocean.pojo.Key>`  |
| `get` | retrieve information about a key by id| `CamelDigitalOceanId` _Integer_| `com.myjeeva.digitalocean.pojo.Key`  |
| `get` | retrieve information about a key by fingerprint| `CamelDigitalOceanKeyFingerprint` _String_| `com.myjeeva.digitalocean.pojo.Key`  |
| `update` | update a key by id| `CamelDigitalOceanId` _Integer_ <br>`CamelDigitalOceanName` _String_| `com.myjeeva.digitalocean.pojo.Key`  |
| `update` | update a key by fingerprint| `CamelDigitalOceanKeyFingerprint` _String_ <br>`CamelDigitalOceanName` _String_| `com.myjeeva.digitalocean.pojo.Key`  |
| `delete` | delete a key by id| `CamelDigitalOceanId` _Integer_ | `com.myjeeva.digitalocean.pojo.Delete`  |
| `delete` | delete a key by fingerprint| `CamelDigitalOceanKeyFingerprint` _String_ | `com.myjeeva.digitalocean.pojo.Delete`  |

## Regions endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` |  list all of the regions that are available | | `List<com.myjeeva.digitalocean.pojo.Region>`  |


## Sizes endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` |  list all of the sizes that are available | | `List<com.myjeeva.digitalocean.pojo.Size>`  |

## Floating IPs endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` |  list all of the Floating IPs available on your account | | `List<com.myjeeva.digitalocean.pojo.FloatingIP>`  |
| `create` |  create a new Floating IP assigned to a Droplet | `CamelDigitalOceanId` _Integer_ | `List<com.myjeeva.digitalocean.pojo.FloatingIP>`  |
| `create` |  create a new Floating IP assigned to a Region | `CamelDigitalOceanRegion` _String_ | `List<com.myjeeva.digitalocean.pojo.FloatingIP>`  |
| `get` | retrieve information about a Floating IP| `CamelDigitalOceanFloatingIPAddress` _String_| `com.myjeeva.digitalocean.pojo.Key`  |
| `delete` | delete a Floating IP and remove it from your account| `CamelDigitalOceanFloatingIPAddress` _String_| `com.myjeeva.digitalocean.pojo.Delete`  |
| `assign` | assign a Floating IP to a Droplet| `CamelDigitalOceanFloatingIPAddress` _String_ <br>`CamelDigitalOceanDropletId` _Integer_| `com.myjeeva.digitalocean.pojo.Action`  |
| `unassign` | unassign a Floating IP | `CamelDigitalOceanFloatingIPAddress` _String_ | `com.myjeeva.digitalocean.pojo.Action`  |
| `listActions` |  retrieve all actions that have been executed on a Floating IP | `CamelDigitalOceanFloatingIPAddress` _String_ | `List<com.myjeeva.digitalocean.pojo.Action>`  |

## Tags endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` |  list all of your tags | | `List<com.myjeeva.digitalocean.pojo.Tag>`  |
| `create` |  create a Tag | `CamelDigitalOceanName` _String_ | `com.myjeeva.digitalocean.pojo.Tag`  |
| `get` |  retrieve an individual tag | `CamelDigitalOceanName` _String_ | `com.myjeeva.digitalocean.pojo.Tag`  |
| `delete` |  delete a tag | `CamelDigitalOceanName` _String_ | `com.myjeeva.digitalocean.pojo.Delete`  |
| `update` |  update a tag | `CamelDigitalOceanName` _String_ <br>`CamelDigitalOceanNewName` _String_| `com.myjeeva.digitalocean.pojo.Tag`  |


## Examples

Get your account info

```
from("direct:getAccountInfo")
    .setHeader(DigitalOceanConstants.OPERATION, constant(DigitalOceanOperations.get))
    .to("digitalocean:account?oAuthToken=XXXXXX")
```

Create a droplet

```
from("direct:createDroplet")
    .setHeader(DigitalOceanConstants.OPERATION, constant("create"))
    .setHeader(DigitalOceanHeaders.NAME, constant("myDroplet"))
    .setHeader(DigitalOceanHeaders.REGION, constant("fra1"))
    .setHeader(DigitalOceanHeaders.DROPLET_IMAGE, constant("ubuntu-14-04-x64"))
    .setHeader(DigitalOceanHeaders.DROPLET_SIZE, constant("512mb"))
    .to("digitalocean:droplet?oAuthToken=XXXXXX")
```

List all your droplets

```
from("direct:getDroplets")
    .setHeader(DigitalOceanConstants.OPERATION, constant("list"))
    .to("digitalocean:droplets?oAuthToken=XXXXXX")
```

Retrieve information for the Droplet (dropletId = 34772987)

```
from("direct:getDroplet")
    .setHeader(DigitalOceanConstants.OPERATION, constant("get"))
    .setHeader(DigitalOceanConstants.ID, 34772987)
    .to("digitalocean:droplet?oAuthToken=XXXXXX")
```

Shutdown  information for the Droplet (dropletId = 34772987)

```
from("direct:shutdown")
    .setHeader(DigitalOceanConstants.ID, 34772987)
    .to("digitalocean:droplet?operation=shutdown&oAuthToken=XXXXXX")
```

