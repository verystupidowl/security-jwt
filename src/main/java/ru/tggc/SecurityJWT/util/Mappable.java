package ru.tggc.SecurityJWT.util;

public interface Mappable<E, D> {

    E toEntity(D dto);

    D toDto(E entity);
}
