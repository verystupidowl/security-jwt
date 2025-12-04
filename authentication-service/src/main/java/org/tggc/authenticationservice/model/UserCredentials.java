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

@Table(name = "usr-credentials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserCredentials {
    @Id
    private Long id;
    private Long userId;
    private String email;
    private String password;
    private Role role;
    private Boolean twoFactorEnabled;
    private Boolean blocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
