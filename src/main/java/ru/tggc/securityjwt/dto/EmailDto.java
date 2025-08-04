package ru.tggc.securityjwt.dto;

import jakarta.validation.constraints.Email;

public record EmailDto(@Email String email) {
}
