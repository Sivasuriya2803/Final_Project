package com.example.demo.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.Dto.QuoteResponseDto;
import com.example.demo.Entity.Quote;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    QuoteMapper INSTANCE = Mappers.getMapper(QuoteMapper.class);

    QuoteResponseDto toDto(Quote entity);
}