package org.tggc.eventservice.config

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.tggc.eventservice.config.interceptor.RolesInterceptor

@Configuration
@RequiredArgsConstructor
open class WebConfig(private val rolesInterceptor: RolesInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(rolesInterceptor)
    }
}
