package org.tggc.userservice.config.interceptor;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.tggc.userservice.aop.annotation.RequiresRoles;

import java.util.Arrays;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

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

        String rolesFromHeader = request.getHeader("X-User-Roles");
        if (StringUtils.isEmpty(rolesFromHeader)) {
            response.setStatus(SC_FORBIDDEN);
            response.getWriter().write("User roles not found");
            return false;
        }

        Set<String> userRoles = Set.of(rolesFromHeader.split(","));

        boolean allowed = Arrays.stream(requiresRoles.value())
                .anyMatch(userRole -> userRoles.contains(userRole.name()));

        if (!allowed) {
            response.setStatus(SC_FORBIDDEN);
            response.getWriter().write("Access denied!");
            return false;
        }

        return true;
    }
}
