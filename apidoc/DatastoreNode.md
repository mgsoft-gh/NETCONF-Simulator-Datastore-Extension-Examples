# class DatastoreNode


The interface represents a node within running configuration and state data tree.

## Public methods

### public java.lang.String getName()


**Returned Value:** The YANG name of the node


### public java.lang.String getNamespace()


**Returned Value:** The YANG namespace the node belongs to


### public java.lang.String getYANGModuleName()


**Returned Value:** The YANG module name where this node is defined



### public java.lang.String getValueNamespace()


**Returned Value:** The node's value namespace if exists, null otherwise


### public java.lang.String getValue()


**Returned Value:** The node's value string representation


### public void setValue(java.lang.Object value)


Sets node's value.


|Parameter Name|Description|
|-----|-----|
|value|Node's value|


### public void setValue(java.lang.Object value, java.lang.String valueNamespace)


Sets node's value and value namespace.


|Parameter Name|Description|
|-----|-----|
|value|Node's value|
|valueNamespace|Node's value namespace|


### public java.lang.String getPath()


**Returned Value:** The node's path including predicates with keys if there is a list node in the path


### public java.lang.String getPathNoKeys()


**Returned Value:** The node's path without any keys. This path without keys should be used for
 effective binding of a datastore node to the data model
 

### public int getInstCount()


**Returned Value:** If this node is a list or a leaf-list node, the method returns the number
 of sibling instances of that specific list or leaf-list nodes in the datastore, 
 including this node
 

### public java.lang.String[][] getInstances()



**Returned Value:** Node instances in a form of an array of arrays of strings,
 carring arrays of key values for each existing instance. List and
 leaf-list nodes may have multiple instances
  


### public java.lang.String[] getKeys()


**Returned Value:** An array of (List) node key values


### public [DatastoreNode](DatastoreNode.md) getParent()


**Returned Value:** The parent node


### public int  getChildrenCount()


**Returned Value:** The number of children nodes


### public [DatastoreNode](DatastoreNode.md) getChild(java.lang.String name)


|Parameter Name|Description|
|-----|-----|
|name|Child node's YANG name|


**Returned Value:** The child node by its given YANG name


### public [DatastoreNode](DatastoreNode.md)[] getChildren()


**Returned Value:** The array of all children nodes


###  public [DatastoreNode](DatastoreNode.md) getNextSibling()


**Returned Value:** The next sibling node if exists, null otherwise


### public java.lang.String getYANGType()


**Returned Value:** YANG type as string. One of the following strings will be returned:
 'YANG_CONTAINER', 'YANG_LEAF', 'YANG_LEAF_LIST', 'YANG_LIST', 'YANG_RPC',
 'YANG_ACTION', 'YANG_NOTIFICATION', 'YANG_INPUT', 'YANG_OUTPUT', 'YANG_ANYDATA', 
 'YANG_ANY_XML' or null if this node is the datastore root node.



### public java.lang.String serializeXML()


**Returned Value:** Node's NETCONF XML string representation.
  
