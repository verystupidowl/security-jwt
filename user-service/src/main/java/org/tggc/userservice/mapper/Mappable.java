package org.tggc.userservice.mapper;

public interface Mappable<E, Rq, Rs> {

    Rs toDto(E entity);

    E toEntity(Rq dto);
}
