# class DatastoreListener


The DatastoreListener class must be extended by a concrete datastore extension implementation class. A single instance of an extension class is created for a YANG profile. Servers simulated by using the same YANG profile share its datastore extension class instance. The implementation must be thread safe as its methods could be called from multiple different threads. 

## Public methods

### public void init(java.lang.String yangProfileName)

This method is called when a YANG profile is loaded in the NETCONF Simulator service. 
This will occur on service start, when a YANG profile is created, modified or enabled. 


|Parameter Name|Description|
|-----|-----|
|yangProfileName|the name of YANG Profile|

### public void cleanup(java.lang.String yangProfileName)

This method is called when a YANG profile is unloaded in the NETCONF Simulator service. 
This will occur on service stop, when a YANG profile is disabled or deleted. 


|Parameter Name|Description|
|-----|-----|
|yangProfileName|the name of YANG Profile|

### public void onServerStart([Session](Session.md) session, [Datastore](Datastore.md) datastore)


This method is called when a simulated server has been started. 


|Parameter Name|Description|
|-----|-----|
|session|session|
|datastore|running configuration and operational state data datastore|


### public void onServerStop([Session](Session.md) session, [Datastore](Datastore.md) datastore)


This method is called when a simulated server has been stopped. 


|Parameter Name|Description|
|-----|-----|
|session|session|
|datastore|running configuration and operational state data datastore|


### public void onRunningConfigChanged(java.lang.String operation, [Session](Session.md) session, [Datastore](Datastore.md) datastore, [DatastoreNode](DatastoreNode.md)[] createdNodes, [DatastoreNode](DatastoreNode.md)[] modifiedNodes, [DatastoreNode](DatastoreNode.md)[] deletedNodes)


This method is called when Running configuration has been changed. 


|Parameter Name|Description|
|-----|-----|
|operation|operation which caused running configuration change ("EDIT-CONFIG", "COPY-CONFIG", "COMMIT", "POST", "PUT", "PATCH", "DELETE", "GNMI-SET").
|session|session|
|datastore|running configuration and operational state data datastore|
|createdNodes|array of the running configuration and operational state data datastore tree nodes created during the current EDIT_CONFIG operation|
|modifiedNodes|array of running configuration and operational state data datastore tree nodes modified during the current EDIT_CONFIG operation|
|deletedNodes|array of running configuration and operational state data datastore tree nodes deleted during the current EDIT_CONFIG operation|


### public void onCustomRPC([Session](Session.md) session, [Datastore](Datastore.md) datastore, [DatastoreNode](DatastoreNode.md) rpcNode)


This method is called when a custom RPC or an Action operation has been executed. 


|Parameter Name|Description|
|-----|-----|
|session|session|
|datastore|running configuration and operational state data datastore|
|rpcNode|RPC or Action data node|

### public void onTimer([Session](Session.md) session, [Datastore](Datastore.md) datastore, java.lang.Object param)


This method os called when a scheduled timer event has occurred. 


|Parameter Name|Description|
|-----|-----|
|session|session|
|datastore|running configuration and operational state data datastore|
|param|timer parameter|


### public void onMessage([Session](Session.md) session, [Datastore](Datastore.md) datastore, java.lang.Object message)


This method is called when a message from an another simulated server has been received. 


|Parameter Name|Description|
|-----|-----|
|session|session|
|datastore|running configuration and operational state data datastore|
|message|message object|


### public void onNBI([Session](Session.md) session, [Datastore](Datastore.md) datastore, java.lang.String nbiName, java.lang.String param)


This method is called when a POST request has been sent to the run-script endpoint through the NETCONF Simulator NBI. 


|Parameter Name|Description|
|-----|-----|
|session|session|
|datastore|running configuration and operational state data datastore|
|nbiName |'script-name' value from POST request's body|
|param |'script-parameter' value from POST request's body|


### public void onActiveScenariosChanged(java.lang.String[] activeScenarios)


This method is called when a YANG profile is loaded or when a scenario is enabled or disabled. DatastoreListener implementation 
may implement different operation modes and switch between them when scenarios are turned off or on. 


|Parameter Name|Description|
|-----|-----|
|activeScenarios|Array of active scenarios names|


### public void setGlobalData(java.lang.String key, java.lang.Object data)


Sets global data 


|Parameter Name|Description|
|-----|-----|
|key|Key|
|data|Data object|


### public Object getGlobalData(java.lang.String key)

Gets global data 

|Parameter Name|Description|
|-----|-----|
|key|Key|

**Returned Value:** Data object or null
