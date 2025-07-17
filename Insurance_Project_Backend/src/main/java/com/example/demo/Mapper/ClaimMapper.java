package com.example.demo.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.Dto.ClaimRequestDto;
import com.example.demo.Dto.ClaimResponseDto;
import com.example.demo.Entity.Claim;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    ClaimMapper INSTANCE = Mappers.getMapper(ClaimMapper.class);

    ClaimResponseDto toDto(Claim entity);

    Claim toEntity(ClaimRequestDto dto);
}
