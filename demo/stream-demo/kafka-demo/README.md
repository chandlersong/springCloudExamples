[Hello world Demo](src/test/java/me/study/springcloud/stream/kafka/HelloWorldTests.java)  
- the hello world test
- how to retrieve kafka meta data, like offset
```java
Long offset = message.getHeaders().get(KafkaHeaders.Long, Long.class);
```
the code is in consumer class and you can retrieve other meta information from header too. 
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

[Stream Demo](src/test/java/me/study/springcloud/stream/kafka/Stream.java)  

- `testWorkCount`: like the hello word for the stream.
- `testBranch`: how to use branch in the stream, for more details,check the class **StreamDemo**  
use Predicate to define the key
```java
Predicate<String, String> output1 = (k, v) -> k.equals("1");
Predicate<String, String> output2 = (k, v) -> k.equals("2");
Predicate<String, String> output3 = (k, v) -> k.equals("3");
```

send the message to different topic, base on the condition above.
```java
input.branch(output1, output2, output3);
```

- `InteractiveQuery`: how to use interactiveQuery,check the class **StreamDemo**    

save the data to locak storage, the key is *Materialized*
```java
.count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("interactiveQuery1")
               .withKeySerde(Serdes.String())
               .withValueSerde(Serdes.Long()));
```

fetch
```java
ReadOnlyKeyValueStore<Object, Object> store = queryService.getQueryableStore("interactiveQuery1",
                                                                                     QueryableStoreTypes.keyValueStore());

KeyValueIterator<Object, Object> all = store.all();
Map<String, Integer> result = Maps.newHashMap();
while (all.hasNext()) {
    KeyValue<Object, Object> next = all.next();
    Object key = next.key;
    Object value = next.value;
    //key is String, value is long
    log.info("key is {},value is {}", key, value);
    result.put(key.toString(), Integer.valueOf(value.toString()));
}
```


