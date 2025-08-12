package org.tggc.authenticationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "usr")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    @Id
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
    private Boolean twoFactorEnabled;
    private Boolean blocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
