## main class
- [AvroMessageConverter](../../../services/avro-entry/src/main/java/me/study/springcloud/io/AvroMessageConverter.java)

## key point
1. use  class **SpecificRecordBase** and  package **org.apache.avro.io** fetch schema automatically and convert to the
data

2. define your own mediaType, the example here is only good choose

3. register the main class to spring to make it can work properly

## reference
[how to register to jackson](https://stackoverflow.com/questions/45898453/apache-avro-with-rest)  
[how the register to spring and customer message convetor](https://callistaenterprise.se/blogg/teknik/2018/09/25/avro-serialization-with-spring-mvc/)