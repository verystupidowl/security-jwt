package ru.tggc.securityjwt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tggc.securityjwt.dto.DemoDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class DemoController {

    @GetMapping("/date")
    public DemoDto getDate() {
        Date now = Date.from(Instant.now());
        return new DemoDto("1", LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()));
    }
}
