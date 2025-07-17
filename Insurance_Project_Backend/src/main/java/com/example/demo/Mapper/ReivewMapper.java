package com.example.demo.Mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.Dto.ReviewRequestDto;
import com.example.demo.Entity.Review;

@Mapper(componentModel = "spring")
public interface ReivewMapper {

    ReivewMapper INSTANCE = Mappers.getMapper(ReivewMapper.class);

    Review toEntity(ReviewRequestDto dto);
}