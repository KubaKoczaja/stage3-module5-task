package com.mjc.school.service.mapper;

import com.mjc.school.model.CommentModel;
import com.mjc.school.service.dto.CommentModelDto;
import com.mjc.school.service.dto.CommentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
@Component
public interface CommentMapper {
		CommentModelDto commentToCommentDto(CommentModel commentModel);
		CommentModel commentDtoToComment(CommentModelDto commentModelDto);
		CommentModel commentRequestToComment(CommentRequestDto commentRequestDto);
}
