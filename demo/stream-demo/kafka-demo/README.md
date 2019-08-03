[Hello world Demo](src/test/java/me/study/springcloud/stream/kafka/HelloWorldTests.java)  
- the hello world test
- how to retrieve kafka meta data, like offset
```java
Long offset = message.getHeaders().get(KafkaHeaders.Long, Long.class);
```
the code is in consumer class and you can retrieve other meta information from header too. 

[Customer binding Demo](src/test/java/me/study/springcloud/stream/kafka/CustomerBindingDemos.java)  
- customer binding examples  
the binder class:
```java
public interface CustomerBinding {

    String INPUT = "myInput";
    String OUTPUT = "myOutput";

    @Output(OUTPUT)
    MessageChannel anOutput();

    @Input(INPUT)
    MessageChannel anInput();
}
```

the producer and consumer class is 

```java
@Slf4j
@RestController
public class MessageController {

    private CustomerBinding binding;

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable("message") String message) {
        try {
            binding.anOutput().send(MessageBuilder.withPayload(message).build());
        } catch (Exception e) {
            log.error("error happened when sending message", e);
        }
    }


    @StreamListener(CustomerBinding.INPUT)
    @SendTo(CustomerBinding.OUTPUT)
    public String process(Message<?> message) {
        String msg = message.getPayload().toString();
        log.info(msg);
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            log.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }

        return msg;
    }


    @Autowired
    public void setBinding(CustomerBinding binding) {
        this.binding = binding;
    }
}
```

like the build-in bindings, the spring stream will auto-create the implementation and inject into the producer you plan to user


[Avro Demo](src/test/java/me/study/springcloud/stream/kafka/AvroExamples.java)  
- how to use avro in kafka
first you need to define a MessageCovert in the configuration.
```java
@Bean
public SchemaRegistryClient schemaRegistryClient(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint) {
    ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
    client.setEndpoint(endpoint);
    return client;
}

@Bean
public MessageConverter userMessageConverter() {
    return new AvroSchemaMessageConverter(MimeType.valueOf("avro/bytes"));
}
```
for the producer, you need to define two bean if you wish everything is automatically.
for the consumer, you only need to define the MessageConverter

next you need to define the schema register in the yml
```yaml
spring:
  cloud:
    stream:
      schemaRegistryClient:
        endpoint: http://cloudTest:19093
      kafka:
        binder:
          schema.registry.url: http://cloudTest:19093
```
and there are two different meaning 
- `spring.cloud.schemaRegistryClient.endpoint`: is for producer, it will automatically register the schema to the server and for other to use
-  `spring.cloud.kafka.binder.schema.registry.url`: where the message converter to read schema. 

if you want to load the schema from local, you can change the configuration in the MessageConverter.