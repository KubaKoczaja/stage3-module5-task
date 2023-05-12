package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorModelDto;
import com.mjc.school.service.dto.AuthorRequestDto;

import java.util.List;

public interface AuthorService extends BaseService<AuthorRequestDto, AuthorModelDto, Long>{
		AuthorModelDto readByNewsId(Long newsId);
		List<AuthorModelDto> readAllPagedAndSorted(int page, int size, String sortBy);
}
