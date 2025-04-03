# class Session


The interface represents a session that is a source of a triggered event. 


## Public methods


### public java.lang.String getClientIP()


Returns this session client IP address. 


**Returned Value:** Client IP address


### public java.lang.String getUsername()


Returns username used to establish this session. 


**Returned Value:** Username used in this session


### public java.lang.String getServerName()


Returns simulated server name. 


**Returned Value:** Server name.


### public java.lang.String getServerIP()


Returns simulated server IP address. 


**Returned Value:** Server IP address.


### public int getServerPort()


Returns simulated server NETCONF port. 


**Returned Value:** Server NETCONF port.


### public void setError()


Sets transaction error. 


|Parameter Name|Description|
|-----|-----|
|errorType|Error type as defined in RFC-6241|
|errorTag|Error tag as defined in RFC-6241|
|errorAppTag|Error app tag as defined in RFC-6241|
|errorPath|Error path|
|errorMessage|Error message|
|errorInfo|Error info|


### public [HTTPClient](HTTPClient.md) http(java.lang.String hostname, int port)


Creates an HTTPClient instance. 


|Parameter Name|Description|
|-----|-----|
|hostname|HTTP server hostname|
|port|HTTP server port|


**Returned Value:** HTTPClient instance.


### public [HTTPClient](HTTPClient.md) https(java.lang.String hostname, int port)


Creates an HTTPClient instance with TLS (HTTPs). 


|Parameter Name|Description|
|-----|-----|
|hostname|HTTP server hostname|
|port|HTTP server port|


**Returned Value:** HTTPClient instance.


### public java.lang.String createCurrentYANGDateTime(int offset)


Creates string representation of current time plus offset. Format is defined in RFC-6021 by date-and-time typedef. 


|Parameter Name|Description|
|-----|-----|
|offset|offset in seconds|



**Returned Value:** Time string 

### public java.lang.String createYANGDateTime(long unixTime)

Converts UNIX time in seconds to the date-and-time formatted string. Format is defined in RFC-6021. 


|Parameter Name|Description|
|-----|-----|
|unixTime|UNIX time in seconds|



**Returned Value:** Time string 

### public long getUnixTimeFromYANGDateTime(java.lang.String yangDateTime)


Converts date-and-time (defined in RFC-6021) formatted string to the UNIX time in seconds.


|Parameter Name|Description|
|-----|-----|
|yangDateTime|date and time string|


**Returned Value:** UNIX time in seconds 


### public void sendNotification([DatastoreNode](DatastoreNdode.md) notification)

**Type:** `public` `void`


Sends notification. 


|Parameter Name|Description|
|-----|-----|
|notification|Notification node|



### public void netconfCallHome(String clientIPAddress, int clientPort)


Establishes a NETCONF call-home TCP connection. 


|Parameter Name|Description|
|-----|-----|
|clientIPAddress|Client IP address|
|clientPort|Client TCP port|

  








