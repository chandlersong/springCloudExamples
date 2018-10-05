package me.study.zuul;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
@EnableZuulProxy
@Configuration
public class SimpleZuulApplication {

    private static final Logger logger = getLogger(SimpleZuulApplication.class);
    public static void main(String[] args) {
        new SpringApplicationBuilder(SimpleZuulApplication.class).run(args);
    }


    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("receive request: simple zuul server");
        return filter;
    }

    @Bean
    public ZuulController createZuulController() {
        return new ZuulController();
    }


    @Bean
    public AddResponseHeaderFilter createFilter() {
        return new AddResponseHeaderFilter();
    }

    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper(
                "ok_(?<name>client\\d)",
                "${name}");
    }

    @Bean
    public FallbackProvider zuulFallbackProvider() {
        return new FallbackProvider() {
            @Override
            public String getRoute() {
                logger.debug("route called");
                return "*";
            }

            @Override
            public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

                logger.debug("route:{},cause:{}", route, cause.getMessage());
                return new ClientHttpResponse() {
                    @Override
                    public HttpStatus getStatusCode() {
                        return HttpStatus.OK;
                    }

                    @Override
                    public int getRawStatusCode() {
                        return 200;
                    }

                    @Override
                    public String getStatusText() {
                        return "OK";
                    }

                    @Override
                    public void close() {

                    }

                    @Override
                    public InputStream getBody() {
                        return new ByteArrayInputStream("fallback".getBytes());
                    }

                    @Override
                    public HttpHeaders getHeaders() {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        return headers;
                    }
                };
            }


        };
    }
}
