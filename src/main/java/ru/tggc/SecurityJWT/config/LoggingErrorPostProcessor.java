package ru.tggc.SecurityJWT.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;
import ru.tggc.SecurityJWT.util.annotations.LoggingError;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ru.tggc.SecurityJWT.util.annotations.LoggingErrorConstants.*;

@Component
@Slf4j
public class LoggingErrorPostProcessor implements BeanPostProcessor {

    Map<String, Class<?>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Method[] methods = beanClass.getMethods();
        if (Arrays.stream(methods).anyMatch(m -> m.isAnnotationPresent(LoggingError.class))) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        Class<?> originalBean = map.get(beanName);
        if (originalBean != null) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(originalBean);
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                Optional<Method> originalMethod = Arrays.stream(originalBean.getMethods())
                        .filter(method::equals)
                        .findFirst();
                if (originalMethod.isPresent()) {
                    LoggingError annotation = originalMethod.get().getAnnotation(LoggingError.class);
                    if (annotation != null) {
                        String time = annotation.time();
                        if (time.equals(ALWAYS) || time.equals(BEFORE)) {
                            log.error(((Throwable) args[0]).getMessage());
                        }
                        Object invoke = proxy.invoke(bean, args);
                        if (time.equals(ALWAYS) || time.equals(AFTER)) {
                            log.error(((Throwable) args[0]).getMessage());
                        }
                        return invoke;
                    }
                }
                return method.invoke(bean, args);
            });
            return enhancer.create();
        }
        return bean;
    }
}
