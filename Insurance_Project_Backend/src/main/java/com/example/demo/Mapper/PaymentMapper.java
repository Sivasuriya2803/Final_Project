package com.example.demo.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.demo.Dto.PaymentResponseDto;
import com.example.demo.Entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	@Mapping(source = "id", target = "paymentId")
	 @Mapping(target = "proposalId", source = "proposal.id")
	    @Mapping(target = "paymentDate", source = "paymentDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
	    PaymentResponseDto toDto(Payment payment);
}
