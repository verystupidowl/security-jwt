package org.tggc.authenticationservice.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.model.UserCredentials;
import org.tggc.authenticationservice.service.PasswordService;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;
import static org.tggc.authenticationservice.model.Role.USER;

@Mapper(componentModel = SPRING, unmappedSourcePolicy = IGNORE, unmappedTargetPolicy = IGNORE)
public interface UserMapper extends Mappable<UserCredentials, RegisterRq> {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "twoFactorEnabled", source = "twoFactorEnabled")
    @Mapping(target = "role", expression = "java(mapRole())")
    @Mapping(target = "password", expression = "java(hashPassword(rq.getPassword(), passwordService))")
    @Mapping(target = "blocked", constant = "false")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    UserCredentials toEntity(RegisterRq rq, @Context PasswordService passwordService);

    default String hashPassword(String password, @Context PasswordService passwordService) {
        return passwordService.hash(password);
    }

    default String mapRole() {
        return USER.name();
    }
}
