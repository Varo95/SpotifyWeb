package com.alvaro.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperVO {
    MapperVO instance = Mappers.getMapper(MapperVO.class);
}
