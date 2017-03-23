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
digitalocean://droplets?operation=list&oAuthToken=XXXXXX
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
| `get` | show information about a Block Storage volume| `CamelDigitalOceanId`| `com.myjeeva.digitalocean.pojo.Volume`  |
| `get` | show information about a Block Storage volume by name| `CamelDigitalOceanName` `CamelDigitalOceanRegion`| `com.myjeeva.digitalocean.pojo.Volume`  |
| `listSnapshots` | retrieve the snapshots that have been created from a volume | `CamelDigitalOceanId` | `List<com.myjeeva.digitalocean.pojo.Snapshot>`  |
| `create` | create a new volume | `CamelDigitalOceanVolumeSizeGigabytes` `CamelDigitalOceanName` `CamelDigitalOceanDescription` `CamelDigitalOceanRegion`| `com.myjeeva.digitalocean.pojo.Volume`  |
| `delete` | delete a Block Storage volume, destroying all data and removing it from your account| `CamelDigitalOceanId`| `com.myjeeva.digitalocean.pojo.Delete`|
| `delete` | delete a Block Storage volume by name| `CamelDigitalOceanName` `CamelDigitalOceanRegion`| `com.myjeeva.digitalocean.pojo.Delete`
| `attach` | attach a Block Storage volume to a Droplet| `CamelDigitalOceanId`  `CamelDigitalOceanDropletId` `CamelDigitalOceanDropletRegion`| `com.myjeeva.digitalocean.pojo.Action`
| `attach` | attach a Block Storage volume to a Droplet by name| `CamelDigitalOceanName`  `CamelDigitalOceanDropletId` `CamelDigitalOceanDropletRegion`| `com.myjeeva.digitalocean.pojo.Action`
| `detach` | detach a Block Storage volume from a Droplet| `CamelDigitalOceanId`  `CamelDigitalOceanDropletId` `CamelDigitalOceanDropletRegion`| `com.myjeeva.digitalocean.pojo.Action`
| `attach` | detach a Block Storage volume from a Droplet by name| `CamelDigitalOceanName`  `CamelDigitalOceanDropletId` `CamelDigitalOceanDropletRegion`| `com.myjeeva.digitalocean.pojo.Action`
| `resize` | resize a Block Storage volume | `CamelDigitalOceanVolumeSizeGigabytes` `CamelDigitalOceanRegion`| `com.myjeeva.digitalocean.pojo.Action`  |
| `listActions` | retrieve all actions that have been executed on a volume | `CamelDigitalOceanId` | `List<com.myjeeva.digitalocean.pojo.Action>`  |


## Droplets endpoint

| operation | Description | Headers | Result |
| ------ | ---- | ------- | ----------- |
| `list` | list all Droplets in your account |  | `List<com.myjeeva.digitalocean.pojo.Droplet>`  |
| `get` | show an individual droplet | `CamelDigitalOceanId` | `com.myjeeva.digitalocean.pojo.Droplet`  |


## Examples


```
from("direct:getAccountInfo")
    .setHeader(DigitalOceanConstants.OPERATION, constant(DigitalOceanOperations.get))
    .to("digitalocean:account?oAuthToken=XXXXXX")
```

