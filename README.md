# Description
this is a demo repository . 
all the example will try to to use a unit test in demo module. the examples I found need to start multiple spring boot 
application, it's how mico service work. but it's not convenient for beginners.

I will try to use one unit test to show one spring cloud feature. but when you read these examples, please remember it's
NOT how to use spring cloud. just how to use some feature..

when you build the project, please use **mvn install -DskipTests**

# Examples

- [Eureka examples](demo/eureka-demo/README.md)
- [Hystrix examples](demo/hystrix-demo/README.md)
- [Ribbon examples](demo/ribbon-demo/README.md)
- [Zuul examples](demo/zuul-demo/README.md)
- [Feign examples](demo/feign-demo/README.md)
- [Config examples](demo/config-demo/README.md)
- [Security examples](demo/security-demo/README.md)
- [Stream examples](demo/stream-demo/README.md)
- [Stream examples](demo/miscellaneous-demo/README.md)


# Extra configuration

## jmx
```yml 
spring:
  jmx:
    default-domain: simplest-eureka-client
``` 
Because all the examples run in same jvm, it need define special domain for each application

  
