# Simple Demo
[Simple Demo](src/test/java/me/demo/springcloud/eureka/SimpleDemo.java)  
this example show how to set metaData and /info page

[Life cycle](src/test/java/me/demo/springcloud/eureka/LifeCycleDemo.java)  
how to listen or use hock for eureka client and server

[Multi-Server](src/test/java/me/demo/springcloud/eureka/MultiServer.java)  
how to configuration cluster of eureka server,
there's three server, 1,2,3. server 1 is the replica of server 2. server 3 will register itself to other's but the other
two doesn't the behavior is different.