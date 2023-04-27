package com.example.springboot.mapstruct.converter;

import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

/**
 * @param <T1> generally should be a Req Entity
 * @param <T2> generally should be a Dto Entity
 * @param <T3> generally should be a Domain Entity
 */
public interface BaseConverter<T1, T2, T3> {

    T3 toDomain(T1 req);

    T3 toDomain(UUID agencyGuid, T1 req);

    T2 toDto(T3 entity);

    List<T3> toDomains(List<T1> reqs);

    List<T2> toDtos(List<T3> entities);

    void merge(T3 entity, @MappingTarget T3 originalEntity);
}
