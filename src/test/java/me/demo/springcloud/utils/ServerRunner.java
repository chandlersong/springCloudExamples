package me.demo.springcloud.utils;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServerRunner {

    private static final String YML_CONFIG_PATH = "spring.config.location=classpath:%1$s";
    private Class<? extends Object> clz;

    private String ymlPath;

    private ServerRunner(Class<? extends Object> clz, String ymlPath) {
        this.clz = clz;
        this.ymlPath = ymlPath;
    }

    public static ServerRunner createAndRunServer(Class<? extends Object> clz, String ymlPath) {
        return (new ServerRunner(clz, ymlPath)).run();
    }

    public ServerRunner run() {
        SpringApplicationBuilder server = new SpringApplicationBuilder(clz)
                .properties(String.format(YML_CONFIG_PATH, ymlPath));
        server.run();

        return this;
    }
}
