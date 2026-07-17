package org.tggc.eventservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.tggc.userapi.api.UserApi
import reactivefeign.spring.config.EnableReactiveFeignClients

@SpringBootApplication
@EnableScheduling
@EnableReactiveFeignClients(clients = [UserApi::class])
open class EventServiceApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(EventServiceApplication::class.java, *args)
        }
    }
}
