package org.tggc.eventservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling
import org.tggc.userapi.api.UserApi

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(clients = [UserApi::class])
open class EventServiceApplication {
    fun main(args: Array<String>) {
        SpringApplication.run(EventServiceApplication::class.java, *args)
    }
}
