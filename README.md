# parroticator

## Overview
This was developed as a test tool for easy communication testing during system development related to socket communication.
The name comes from parrot + communicator.  

## Function
Parroticator provides the socket server and reply completely same message that received.  
```
client -(hello world)-> TcpInboundGateway -> parroticator service -> TcpOutboundGateway -(hello world)-> Client 
```  

### Specification of message
The message must be appended the STX and ETX for beginning and the end of the message.  
For example, "Hello world" must be STX + "Hello world".toByte() + ETX.
STX is defined by 0x02 and ETX is 0x03.  

### Settings
Parroticator uses the port number 8080 for Spring boot server and 1000 for socket server.  
The port number for socket receiver process can be changed by the definition on application.properties.  

Also, default message length can be modified by the definition on application.properties.
Here is an example of settings.  
```properties
zoeque.parroticator.message.length=1024
zoeq.parroticator.server.port=1000
```  

## Development
This tool is developed by my studying for Spring boot and other APIs.  
Also, I attempt to build good architecture and design.  

### Environment
- IntelliJ IDEA community edition 2023.2
- OpenJDK 17
- Spring Integration 3.1.1
- Spring boot 3.1.1