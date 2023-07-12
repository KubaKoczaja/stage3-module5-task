package com.mjc.school.service.implementation;

import com.mjc.school.model.NewsModel;
import com.mjc.school.model.TagModel;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.NewsModelDto;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
		private final NewsRepository newsRepository;
		private final AuthorService authorService;
		private final AuthorMapper authorMapper;
		private final TagService tagService;
		private final TagMapper tagMapper;
		private final NewsMapper newsMapper;

		@Override
		public List<NewsModelDto> readAll() {
				return newsRepository.findAll().stream().map(newsMapper::newsToNewsDTO).collect(Collectors.toList());
		}
		@Override
		public List<NewsModelDto> readAllPagedAndSorted(int page, int size, String sortBy) {
				String[] split = sortBy.split("::");
				return newsRepository
								.findAll(PageRequest
												.of(page - 1, size, split[1].equals("asc") ? Sort.by(split[0]).ascending() : Sort.by(split[0]).descending()))
								.getContent()
								.stream()
								.map(newsMapper::newsToNewsDTO)
								.collect(Collectors.toList());
		}

		@Override
		public NewsModelDto readById(Long id) {
				return newsMapper
								.newsToNewsDTO(newsRepository
												.findById(id)
												.orElseThrow(() -> new NoSuchEntityException("No news with such id!")));
		}

		@Override
		public NewsModelDto create(NewsRequestDto createRequest) {
				createRequest.setCreateDate(LocalDateTime.now());
				createRequest.setLastUpdateDate(LocalDateTime.now());
				NewsModel savedNews = newsMapper.newsRequestToNews(createRequest);
    		savedNews.setAuthorModel(authorMapper.authorDtoToAuthor(authorService.readById(createRequest.getAuthorId())));
				if (!createRequest.getTagIds().isBlank()) {
						Set<TagModel> collect =
										Arrays.stream(createRequest.getTagIds().split(","))
														.map(t -> tagMapper.tagDTOToTag(tagService.readById(Long.valueOf(t))))
														.collect(Collectors.toSet());
						savedNews.setTagModelSet(collect);
				}
				return newsMapper.newsToNewsDTO(newsRepository.save(savedNews));
		}

		@Override
		public NewsModelDto update(NewsRequestDto updateRequest) {
				NewsModel newsFromDatabase = newsRepository.findById(updateRequest.getId())
								.orElseThrow(() -> new NoSuchEntityException("No such news!"));
    		newsFromDatabase.setTitle(updateRequest.getTitle());
				newsFromDatabase.setContent(updateRequest.getContent());
				newsFromDatabase.setLastUpdateDate(LocalDateTime.now());
				if (!updateRequest.getTagIds().isBlank()) {
						Set<TagModel> collect =
										Arrays.stream(updateRequest.getTagIds().split(","))
														.map(t -> tagMapper.tagDTOToTag(tagService.readById(Long.valueOf(t))))
														.collect(Collectors.toSet());
						newsFromDatabase.setTagModelSet(collect);
				}
				newsFromDatabase.setAuthorModel(authorMapper.authorDtoToAuthor(authorService.readById(updateRequest.getAuthorId())));
				return newsMapper.newsToNewsDTO(newsRepository.save(newsFromDatabase));
		}

		@Override
		public boolean deleteById(Long id) {
				newsRepository.deleteById(id);
				return true;
		}

		@Override
		public Set<NewsModelDto> readNewsByVariousParameters(NewsRequestDto newsRequestDto) {
				Set<NewsModel> searchResult = new HashSet<>();
				if (!newsRequestDto.getTagNames().isBlank()) {
						Arrays.stream(newsRequestDto.getTagNames().split(","))
										.map(newsRepository::findAllByTagModelName)
										.forEach(searchResult::addAll);
				}
				if (!newsRequestDto.getTagIds().isBlank()) {
						Arrays.stream(newsRequestDto.getTagIds().split(","))
										.map(s -> newsRepository.findAllByTagModelId(Long.valueOf(s)))
										.forEach(searchResult::addAll);
				}
    if (!newsRequestDto.getAuthorName().isBlank()) {
				searchResult.addAll(newsRepository.findAllByAuthorModelName(newsRequestDto.getAuthorName()));
		}
		if (!newsRequestDto.getTitle().isBlank()) {
				searchResult.addAll(newsRepository.findAllByTitle(newsRequestDto.getTitle()));
		}
		if (!newsRequestDto.getContent().isBlank()) {
				searchResult.addAll(newsRepository.findAllByContent(newsRequestDto.getContent()));
		}
				return searchResult.stream().map(newsMapper::newsToNewsDTO).collect(Collectors.toSet());
		}
}