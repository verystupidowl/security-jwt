package ru.tggc.securityjwt.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tggc.securityjwt.dto.notification.NotificationType;

@FeignClient(name = "code", url = "http://localhost:8090/notification/code")
public interface CodeApi {

    @GetMapping("/get-code")
    String getCode(@RequestParam("email") String email, @RequestParam NotificationType notificationType);

    @DeleteMapping
    void deleteCode(@RequestParam("email") String email, @RequestParam NotificationType notificationType);
}
