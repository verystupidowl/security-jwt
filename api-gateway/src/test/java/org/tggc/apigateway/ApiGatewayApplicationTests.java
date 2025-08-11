package org.tggc.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tggc.authapi.api.AuthApi;

@SpringBootTest
@Slf4j
class ApiGatewayApplicationTests {
    @Autowired
    AuthApi authApi;

    @Test
    void contextLoads() {
        System.out.println(authApi);
    }

}
