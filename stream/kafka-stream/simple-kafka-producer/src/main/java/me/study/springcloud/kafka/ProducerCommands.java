package me.study.springcloud.kafka;

import lombok.Data;
import me.study.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ConcurrentLinkedQueue;

@Data
@ShellComponent
public class ProducerCommands {


    @Autowired
    private StreamBridge bridge;


    private ConcurrentLinkedQueue<User> names = new ConcurrentLinkedQueue<>();


    @ShellMethod("send by queue")
    public void sendQueue(@RequestParam String name) {
        User.Builder builder = User.newBuilder();
        builder.setName(name);
        builder.setFavoriteColor("bbbb");
        builder.setFavoriteNumber(2);
        names.add(builder.build());
    }

    @ShellMethod("send by bridge")
    public void sendBridge(@RequestParam String name) {
        User.Builder builder = User.newBuilder();
        builder.setName(name);
        builder.setFavoriteColor("bbbb");
        builder.setFavoriteNumber(2);
        bridge.send("avroTopic", builder.build(), MimeType.valueOf("application/*+avro"));
    }


}
