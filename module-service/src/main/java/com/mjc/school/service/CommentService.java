package com.mjc.school.service;

import com.mjc.school.service.dto.CommentModelDto;
import com.mjc.school.service.dto.CommentRequestDto;

import java.util.List;

public interface CommentService extends BaseService<CommentRequestDto, CommentModelDto, Long>{
		List<CommentModelDto> readByNewsId(Long newsId);
		List<CommentModelDto> readAllPagedAndSorted(int page, int size, String sortBy);
}
