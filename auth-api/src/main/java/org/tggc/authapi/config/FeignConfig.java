package org.tggc.authapi.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.tggc.authapi.exception.FeignErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class FeignConfig {

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return (s, response) -> {
            String body;
            try {
                body = extractBody(response);
                return new FeignErrorException(
                        HttpStatus.valueOf(response.status()),
                        body
                );
            } catch (IOException e) {
                log.error(e.getMessage());
                return new FeignErrorException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage()
                );
            }
        };
    }

    private String extractBody(Response response) throws IOException {
        if (response.body() != null) {
            return new BufferedReader(new InputStreamReader(response.body().asInputStream()))
                    .lines().collect(Collectors.joining("\n"));
        }
        return "No error body";
    }
}
