# class Datastore

The interface represents simulated server's running configuration and state data datastore. 
This interface defines methods for manipulation of the running configuration and state data tree, scheduling timers, sending notifications, establishing call-home sessions and sharing data between different simulated servers by exchanging messages. 

## Public methods


### public [DatastoreNode](DatastoreNdode.md) getNode(java.lang.String path)

Finds node in the datastore tree for a given absolute path. 


|Parameter Name|Description|
|-----|-----|
|path|Absolute path to the node|

**Returned Value:** Selected DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) getNode([DatastoreNode](DatastoreNdode.md) currentNode, java.lang.String path)


Finds node in the datastore tree for given current node and relative path. 


|Parameter Name|Description|
|-----|-----|
|currentNode|Current DatastoreNode object|
|path|Relative path from currentNode|


**Returned Value:** Selected DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) createNode(java.lang.String path)


Finds or creates (if not exists) node in the datastore tree for a given absolute path. 


|Parameter Name|Description|
|-----|-----|
|path|Absolute path to the node|


**Returned Value:** Created or existing DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) createNodeInsertBefore(java.lang.String path, java.lang.String insertPointPath)


Finds or creates (if not exists) node in the datastore tree for given current node and relative path. 
Created node will be inserted before the node pointed by insertPointPath path. 


|Parameter Name|Description|
|-----|-----|
|path|Absolute path to the node|
|insertPointPath|Insert point absolute path |


**Returned Value:** Created or existing DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) createNodeInsertAfter(java.lang.String path, java.lang.String insertPointPath)


Finds or creates (if not exists) node in the datastore tree for given absolute path and insert point path. 
Created node will be inserted after the node pointed by insertPointPath path. 


|Parameter Name|Description|
|-----|-----|
|path|Absolute path to the node|
|insertPointPath|Insert point absolute path|


**Returned Value:** Created or existing DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) createNode([DatastoreNode](DatastoreNdode.md) currentNode, java.lang.String path)


Finds or creates (if not exists) node in the datastore tree for given current node and relative path. 


|Parameter Name|Description|
|-----|-----|
|currentNode|Current DatastoreNode object|
|path|Relative Path from currentNode|


**Returned Value:** Created or existing DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) createNodeInsertBefore([DatastoreNode](DatastoreNdode.md) currentNode, java.lang.String path, java.lang.String insertPointPath)


Finds or creates (if not exists) node in the datastore tree for given current node, relative path and insert point. 
Created node will be inserted before the node pointed by insertPointPath path. 


|Parameter Name|Description|
|-----|-----|
|currentNode|Current DatastoreNode object|
|path|Relative Path from currentNode|
|insertPointPath|Insert point relative path|


**Returned Value:** Created or existing DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) createNodeInsertAfter([DatastoreNode](DatastoreNdode.md) currentNode, java.lang.String path, java.lang.String insertPointPath)


Finds or creates (if not exists) node in the datastore tree for given current node, relative path and insert point. 
Created node will be inserted after the node pointed by insertPointPath path. 


|Parameter Name|Description|
|-----|-----|
|currentNode|Current DatastoreNode object|
|path|Relative Path from currentNode|
|insertPointPath|Insert point relative path|


**Returned Value:** Created or existing DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) createNotification(java.lang.String path)


Creates notification node for a given absolute path. 


|Parameter Name|Description|
|-----|-----|
|path|Absolute path to the notification node|


**Returned Value:** Created DatastoreNode object or null


### public [DatastoreNode](DatastoreNdode.md) createNotification([DatastoreNode](DatastoreNdode.md) currentNode, java.lang.String path)


Creates notification node for given current node and relative path. 


|Parameter Name|Description|
|-----|-----|
|currentNode|Current DatastoreNode object|
|path|Relative Path from currentNode|


**Returned Value:** Created DatastoreNode object or null


### public boolean deleteNode(java.lang.String path)


Deletes node for a given absolute path.


|Parameter Name|Description|
|-----|-----|
|path|Absolute path to the node|


**Returned Value:** true if the succeeded, false if failed 


### public boolean deleteNode([DatastoreNode](DatastoreNdode.md) currentNode, java.lang.String path)


Deletes node for given current node and relative path.


|Parameter Name|Description|
|-----|-----|
|currentNode|Current DatastoreNode object|
|path|Relative Path from currentNode|


**Returned Value:** true if the succeeded, false if failed 


###  public [DatastoreNode](DatastoreNdode.md) getRootNode()


Returns root datastore node. 


**Returned Value:** Root datastore node.
  

## public [DatastoreNode](DatastoreNdode.md)[] selectNodes(java.lang.String xpath)


XPath based node selector. According to the XPAH specification referenced in RFC-7950 
(http://www.w3.org/TR/1999/REC-xpath-19991116) 


|Parameter Name|Description|
|-----|-----|
|xpath|XPath expression|


**Returned Value:** Array of selected nodes or null if selection is empty


### public boolean isLockedByNETCONFClient()

**Type:** `public` `boolean`


Checks if datastore is explicitly locked by a NETCONF user. 


**Returned Value:** true if locked, false otherwise


### public void scheduleTimer(int delay, java.lang.Object reference)


Schedules a timer event. The [DatastoreLisener](DatastoreListener.md) class method **onTimer** will be called when timer ends. 


|Parameter Name|Description|
|-----|-----|
|delay|Delay in milliseconds.|
|reference|Reference object which will be passed as param parameter of the [DatastoreLisener](DatastoreListener.md) class method **onTimer**.|



### public void sendMessageToAnotherServer(java.lang.String serverName, java.lang.Object message)


Sends a message (an object) to an another simulated server. A target simulated server will be notified by 
a call to the [DatastoreLisener](DatastoreListener.md) class method **onMessage**. The message object will be delivered through the method parameter. 


|Parameter Name|Description|
|-----|-----|
|serverName|The name of the target simulated server|
|message|Message object|


