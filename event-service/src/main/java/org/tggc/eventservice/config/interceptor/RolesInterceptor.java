package org.tggc.eventservice.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.tggc.eventservice.aop.annotation.RequiresRoles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class RolesInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        RequiresRoles requiresRoles = method.getMethodAnnotation(RequiresRoles.class);
        if (requiresRoles == null) {
            requiresRoles = method.getBeanType().getAnnotation(RequiresRoles.class);
        }

        if (requiresRoles == null) {
            return true;
        }

        String rolesHeader = request.getHeader("X-User-Roles");
        if (rolesHeader == null || rolesHeader.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("User roles not found");
            return false;
        }

        Set<String> userRoles = new HashSet<>(Arrays.asList(rolesHeader.split(",")));

        boolean allowed = Arrays.stream(requiresRoles.value())
                .anyMatch(userRole -> userRoles.contains(userRole.name()));

        if (!allowed) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access denied: insufficient role");
            return false;
        }

        return true;
    }
}
