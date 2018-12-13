/*
 * Copyright (c) 2018
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2018-12-13T20:59:18.202+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.jwtresource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;


@SpringBootApplication
public class JwtResourceApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(JwtResourceApplicationServer.class, args);
    }

    @Bean
    public JwtTimestampValidator createTimeValiadator() {
        return new JwtTimestampValidator(Duration.of(0, ChronoUnit.SECONDS));
    }

    @Bean
    @ConditionalOnProperty(name = "spring.security.oauth2.resourceserver.jwt.jwk-set-uri")
    public JwtDecoder jwtDecoderByJwkKeySetUri(@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwturi) {
        NimbusJwtDecoderJwkSupport nimbusJwtDecoderJwkSupport = new NimbusJwtDecoderJwkSupport(jwturi);

        JwtTimestampValidator jwtTimestampValidator = new JwtTimestampValidator(Duration.of(0, ChronoUnit.SECONDS));
        nimbusJwtDecoderJwkSupport.setJwtValidator(new DelegatingOAuth2TokenValidator<>(Collections.singletonList(jwtTimestampValidator)));
        return nimbusJwtDecoderJwkSupport;
    }
}
