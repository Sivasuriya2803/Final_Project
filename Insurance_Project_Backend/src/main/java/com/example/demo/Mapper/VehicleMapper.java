package com.example.demo.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.Dto.VehicleDto;
import com.example.demo.Entity.Vehicle;
@Mapper(componentModel = "spring")
public interface VehicleMapper {


    Vehicle toEntity(VehicleDto dto);

    @Mapping(target = "userId", source = "user.id")
    VehicleDto toDto(Vehicle vehicle);
}
