package me.demo.springcloud.utils;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

public class ServerRunner {

    private static final String YML_CONFIG_PATH = "spring.config.location=classpath:%1$s";
    private Class<? extends Object> clz;

    private Optional<String> ymlPath;
    private Optional<ConfigurableApplicationContext> application = Optional.empty();


    private ServerRunner(Class<? extends Object> clz, String ymlPath) {
        this.clz = clz;
        this.ymlPath = Optional.ofNullable(ymlPath);
    }

    private ServerRunner(Class<? extends Object> clz) {
        this(clz, null);
    }

    public static ServerRunner createAndRunServer(Class<? extends Object> clz, String ymlPath) {
        return (new ServerRunner(clz, ymlPath)).run();
    }

    public static ServerRunner createAndRunServer(Class<? extends Object> clz) {
        return (new ServerRunner(clz)).run();
    }

    public ServerRunner run() {
        SpringApplicationBuilder server = new SpringApplicationBuilder(clz);
        ymlPath.ifPresent(path -> {
            server.properties(String.format(YML_CONFIG_PATH, path));
        });
        application = Optional.ofNullable(server.run());

        return this;
    }

    public void stop() {
        application.ifPresent(ConfigurableApplicationContext::close);
    }
}
