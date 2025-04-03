# MG-SOFT NETCONF Simulator Datastore Extension Example (IETF Interfaces)

## Introduction

This repository contains an official example for the **MG-SOFT NETCONF Simulator Datastore Extension**. The **MG-SOFT NETCONF Simulator Datastore Extension API** is a Java interface that enables users to program live behaviors of NETCONF/RESTCONF/gNMI servers simulated using the [**MG-SOFT NETCONF Simulator application**](https://www.mg-soft.si/mgNetConfSimulator.html). API documentation is available in this repository ([link](apidoc)). You can examine, play with or adapt from this example to create your own extension. 

> [!NOTE]
>
> You can download the **MG-SOFT NETCONF Simulator** application from this [link](https://www.mg-soft.si/download.html?product=netconfsimulator).
>
> **MG-SOFT NETCONF Simulator** version 11 or higher is required.
>
> **MG-SOFT NETCONF Simulator** is a Java(TM) application that can be used on 64-bit MS Windows and Linux operating systems with 64-bit Java Runtime Environment version 8 or later installed.
>
> Contact the **MG-SOFT Sales** team (sales@mg-soft.si) to obtain a license. A 30-day evaluation is also available.
 

## Getting Started

This example project contains the following files:
* [DatastoreExampleIETFInterfaces.java](src/main/java/com/mgsoft/netconfsimulator/examples/interfaces/InterfacesDatastoreTraffic.java),
* [ObjectStorage.java](src/main/java/com/mgsoft/netconfsimulator/examples/interfaces/InterfacesDatastoreTraffic.java) and 
* [pom.xml](pom.xml).

### Dependencies
The only dependency used is the **MG-SOFT NETCONF Simulator Datastore Extension API** jar. The jar file is available from the **MG-SOFT** repository ([link](https://secure.mg-soft.com/files/mgnetconfsimulator/com/mgsoft/netconfsimulator/mgncdatastoreapi/1.0.0/mgncdatastoreapi-1.0.0.jar)). The Javadoc file is also available ([link](https://secure.mg-soft.com/files/mgnetconfsimulator/com/mgsoft/netconfsimulator/mgncdatastoreapi/1.0.0/mgncdatastoreapi-1.0.0-javadoc.jar)). The [pom.xml](pom.xml) contains dependencies and **MG-SOFT** repository configuration for Maven build. 

### Build and Deploy

Clone or download the project and build ncsimexample-1.0-SNAPSHOT.jar file. Then, load the built jar file into the **MG-SOFT Netconf Simulator GUI** (Edit->Extensions). In a YANG Profile that contains **ietf-interfaces@2018-02-20.yang** module, select **com.mgsoft.netconfsimulator.examples.interfaces.DatastoreExampleIETFInterfaces** in the **Datastore extension** combo box and apply the changes. Please check the **MG-SOFT NETCONF Simulator** user manual for details ([link](https://www.mg-soft.si/files/NETCONF_Simulator.pdf)).

## What does this example extension do?

This extension example is based on the **ietf-interfaces@2018-02-20.yang** standard YANG module. It monitors the configuration of interfaces and drives their state data. The interface's state data leafs **admin-status** and **oper-status** values are set based on the interface's **enabled** configuration leaf value. A persistent value is assigned to the interface's state data leaf **if-index**. Assigned interfaces' indices are saved in a file and loaded on startup. Additionally, interfaces' traffic counters are periodically increased. 

The **DatastoreExampleIETFInterfaces** class extends **com.mgsoft.ncsim.datastoreapi.DatastoreListener** API class. **The MG-SOFT NETCONF Simulator** service calls **DatastoreExampleIETFInterfaces** public methods on different events. When the **onServerStart** or **onRunningConfigChanged** methods are called, the example checks the interface configuration if a new interface has been added or if the **enabled** leaf of an existing interface has been changed. The state data leafs are updated accordingly as described above. On a timer event, when the **onTimer** method is called, the traffic counters are updated by the example. Timer is scheduled in the example's **onServerStart** method and then rescheduled in the **onTimer** method. In the example's **init** method, interfaces' indices values are loaded from file. In the example's **cleanup** method assigned indices are stored to the file. The **ObjectStorage.java** class implements some helper methods for Object to/from file serilization. It is used for writing/reading interfaces' indices to/from a file.


## License
This example is licensed under the terms of Apache License version 2.0.
