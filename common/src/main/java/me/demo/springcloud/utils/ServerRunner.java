/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-10-11T00:00:57.609+08:00
 * LGPL licence
 *
 */

package me.demo.springcloud.utils;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

public class ServerRunner {

    private static final String YML_CONFIG_PATH = "spring.config.location=classpath:%1$s";
    private static final String BOOTSTRAP_CONFIG_PATH = "spring.cloud.bootstrap.location=classpath:%1$s";
    private Class<?> clz;

    private Optional<String> ymlPath;
    private Optional<String> bootstrap;

    private Optional<ConfigurableApplicationContext> application = Optional.empty();


    private ServerRunner(Class<?> clz, String ymlPath, String bootstrap) {
        this.clz = clz;
        this.ymlPath = Optional.ofNullable(ymlPath);
        this.bootstrap = Optional.ofNullable(bootstrap);
    }

    private ServerRunner(Class<?> clz) {
        this(clz, null, null);
    }

    public static ServerRunner createAndRunServer(Class<?> clz, String ymlPath) {
        return createAndRunServer(clz, ymlPath, null);
    }

    public static ServerRunner createAndRunServer(Class<?> clz, String ymlPath, String bootstrap) {
        return (new ServerRunner(clz, ymlPath, bootstrap)).run();
    }

    public static ServerRunner createAndRunServer(Class<?> clz) {
        return (new ServerRunner(clz)).run();
    }

    public ServerRunner run() {
        SpringApplicationBuilder server = new SpringApplicationBuilder(clz);
        ymlPath.ifPresent(path -> server.properties(String.format(YML_CONFIG_PATH, path)));

        bootstrap.ifPresent(path -> server.properties(String.format(BOOTSTRAP_CONFIG_PATH, path)));
        application = Optional.ofNullable(server.run());

        return this;
    }

    public void stop() {
        application.ifPresent(ConfigurableApplicationContext::close);
    }
}
