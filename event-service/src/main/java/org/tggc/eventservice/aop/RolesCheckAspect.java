package org.tggc.eventservice.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tggc.eventservice.aop.annotation.RequiresRoles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class RolesCheckAspect {

    @Around("@annotation(requiresRoles)")
    public Object checkRoles(ProceedingJoinPoint joinPoint, RequiresRoles requiresRoles) throws Throwable {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new RuntimeException("No request attributes");
        }

        HttpServletRequest request = attrs.getRequest();
        String rolesHeader = request.getHeader("X-User-Roles");
        if (rolesHeader == null || rolesHeader.isEmpty()) {
            throw new SecurityException("No roles found in request");
        }

        Set<String> userRoles = new HashSet<>(Arrays.asList(rolesHeader.split(",")));

        boolean hasRole = Arrays.stream(requiresRoles.value())
                .anyMatch(userRole -> userRoles.contains(userRole.name()));

        if (!hasRole) {
            throw new SecurityException("User does not have required role");
        }

        return joinPoint.proceed();
    }
}
