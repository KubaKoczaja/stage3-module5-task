package com.mjc.school.service;

import com.mjc.school.service.dto.NewsModelDto;
import com.mjc.school.service.dto.NewsRequestDto;

import java.util.List;
import java.util.Set;

public interface NewsService extends BaseService<NewsRequestDto, NewsModelDto, Long> {
		Set<NewsModelDto> readNewsByVariousParameters(NewsRequestDto newsRequestDto);
		List<NewsModelDto> readAllPagedAndSorted(int page, int size, String sortBy);
}
