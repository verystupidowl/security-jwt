package org.tggc.userapi.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tggc.userapi.dto.UserDto;

import java.util.List;

@FeignClient(name = "userApi", url = "/api/v1/user")
public interface UserApi {

    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable("id") long id);

    @PostMapping("/getByIds")
    List<UserDto> getUsers(@RequestBody List<Long> ids);
}
