# class HttpClient


The HTTPClient defines HTTP(s) client interface. 


## Public Methods


### public HttpClient withContentTypeJSON()


Sets Content-Type=application/json HTTP header. 

**Returned Value:** Self


### public HttpClient withContentTypeXML()


Sets Content-Type=application/xml HTTP header. 


**Returned Value:** Self



### public HttpClient withContentTypePlainText()


Sets Content-Type=text/plain HTTP header. 


**Returned Value:** Self


### public HttpClient withContentType(java.lang.String contentType)


Sets custom Content-Type HTTP header. 


|Parameter Name|Description|
|-----|-----|
|contentType|Content-Type value|

**Returned Value:** Self


### public HttpClient withHeader(java.lang.String jey, java.lang.String value)


Sets custom HTTP header key-value pair. 


|Parameter Name|Description|
|-----|-----|
|key|HTTP header key|
|value|HTTP header value|


**Returned Value:** Self


### public HttpClient withBody(java.lang.String body)


Sets HTTP body. 


|Parameter Name|Description|
|-----|-----|
|body|HTTP body|


**Returned Value:** Self
 

### public HttpClient withBasicAuth(java.lang.String username, java.lang.String password)


Sets basic HTTP authentication. 


|Parameter Name|Description|
|-----|-----|
|username|Username|
|password|Password|


**Returned Value:** Self


### public HttpClient withWait()


Sets synchronous mode of operation. 


**Returned Value:** Self


### public HttpClient get(java.lang.String uri)


Invokes HTTP GET operation. 



|Parameter Name|Description|
|-----|-----|
|uri|URI|


**Returned Value:** HTTPClient object containing HTTP response or null if timeout occurs or asynchronous mode.


### public HttpClient post(java.lang.String uri)


Invokes HTTP POST operation. 


|Parameter Name|Description|
|-----|-----|
|uri|URI|


**Returned Value:** HTTPClient object containing HTTP response or null if timeout occurs or asynchronous mode.


### public HttpClient patch(java.lang.String uri)


Invokes HTTP PATCH operation. 


|Parameter Name|Description|
|-----|-----|
|uri|URI|


**Returned Value:** HTTPClient object containing HTTP response or null if timeout occurs or asynchronous mode.


### public HttpClient put(java.lang.String uri)


Invokes HTTP PUT operation. 


|Parameter Name|Description|
|-----|-----|
|uri|URI|


**Returned Value:** HTTPClient object containing HTTP response or null if timeout occurs or asynchronous mode.


### public HttpClient delete(java.lang.String uri)


Invokes HTTP DELETE operation. 


|Parameter Name|Description|
|-----|-----|
|uri|URI|


**Returned Value:** HTTPClient object containing HTTP response or null if timeout occurs or asynchronous mode.
 

### public HttpClient head(java.lang.String uri)


Invokes HTTP HEAD operation. 


|Parameter Name|Description|
|-----|-----|
|uri|URI|


**Returned Value:** HTTPClient object containing HTTP response or null if timeout occurs or asynchronous mode.


### public int getStatus()

Gets HTTP status code. 

**Returned Value:** HTTP status code. 


### public java.lang.String getBody()


Gets HTTP body content. 


## java.lang.String[] getHeaderKeys()


Gets all header keys. 


**Returned Value:** Array of all header keys.


### public java.lang.String getHeaderValue(java.lang.String key)


Get a header value for a given key. 


|Parameter Name|Description|
|-----|-----|
|key|Header key|


**Returned Value:** Header value for a given key.





