# Description
this is a demo repository . 
all the example will try to to use a unit test in demo module. the examples I found need to start multiple spring boot 
application, it's how mico service work. but it's not convenient for beginners.

I will try to use one unit test to show one spring cloud feature. but when you read these examples, please remember it's
not how to use spring cloud work. just some examples.


# Examples

- [Eureka examples](demo/EurekaDemo/guide.md)



# Extra configuration

## jmx
```yml 
spring:
  jmx:
    default-domain: simplest-eureka-client
``` 
Because all the examples run in same jvm, it need define special domain for each application

  
