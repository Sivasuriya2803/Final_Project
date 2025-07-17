package com.example.demo.Mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.demo.Dto.ProposalStatusDto;
import com.example.demo.Entity.Proposal;

@Mapper(componentModel = "spring")
public interface ProposalMapper {

    @Mapping(target = "proposalId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "submittedAt", source = "submittedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "quoteGeneratedAt", source = "quoteGeneratedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ProposalStatusDto toStatusDto(Proposal proposal);
}
