package org.tggc.eventservice.config.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.tggc.eventservice.aop.annotation.RequiresRoles

@Component
class RolesInterceptor : HandlerInterceptor {

    @Throws(Exception::class)
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }

        var requiresRoles = handler.getMethodAnnotation(RequiresRoles::class.java)
        if (requiresRoles == null) {
            requiresRoles = handler.beanType.getAnnotation(RequiresRoles::class.java)
        }

        if (requiresRoles == null) {
            return true
        }

        val rolesHeader = request.getHeader("X-User-Roles")
        if (rolesHeader == null || rolesHeader.isEmpty()) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.writer.write("User roles not found")
            return false
        }

        val userRoles = HashSet(
            listOf(
                *rolesHeader.split(",".toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray())
        )

        val allowed = requiresRoles.value.any { it.name in userRoles }

        println(userRoles)
        println(rolesHeader)

        if (!allowed) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.writer.write("Access denied: insufficient role")
            return false
        }

        return true
    }
}
