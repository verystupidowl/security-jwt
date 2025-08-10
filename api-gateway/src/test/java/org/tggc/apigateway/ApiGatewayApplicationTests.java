package org.tggc.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
        log.info(this.getClass().getName());
    }

}
