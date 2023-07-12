package com.mjc.school.service.implementation;

import com.mjc.school.model.AuthorModel;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorModelDto;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
		private final AuthorRepository authorRepository;
		private final AuthorMapper authorMapper;
		@Override
		public List<AuthorModelDto> readAll() {
				return authorRepository.findAll().stream().map(authorMapper::authorToAuthorDto).collect(Collectors.toList());
		}

		@Override
		public List<AuthorModelDto> readAllPagedAndSorted(int page, int size, String sortBy) {
				String[] split = sortBy.split("::");
				return authorRepository
								.findAll(PageRequest
												.of(page - 1, size, split[1].equals("asc") ? Sort.by(split[0]).ascending() : Sort.by(split[0]).descending()))
								.getContent()
								.stream()
								.map(authorMapper::authorToAuthorDto)
								.collect(Collectors.toList());
		}

		@Override
		public AuthorModelDto readById(Long id) {
				return authorMapper.authorToAuthorDto(authorRepository
												.findById(id)
												.orElseThrow(() -> new NoSuchEntityException("No author with such id!")));
		}

		@Override
		public AuthorModelDto create(AuthorRequestDto createRequest) {
				createRequest.setCreateDate(LocalDateTime.now());
				createRequest.setLastUpdateDate(LocalDateTime.now());
				AuthorModel savedAuthor = authorMapper.authorRequestToAuthor(createRequest);
				return authorMapper.authorToAuthorDto(authorRepository.save(savedAuthor));
		}

		@Override
		public AuthorModelDto update(AuthorRequestDto updateRequest) {
				AuthorModel authorFromDatabase = authorRepository.findById(updateRequest.getId())
								.orElseThrow(() -> new NoSuchEntityException("No author with such id!"));
   		  authorFromDatabase.setName(updateRequest.getName());
				authorFromDatabase.setLastUpdateDate(LocalDateTime.now());
				return authorMapper.authorToAuthorDto(authorRepository.save(authorFromDatabase));
		}

		@Override
		@OnDelete
		public boolean deleteById(Long id) {
				authorRepository.deleteById(id);
				return true;
		}

		@Override
		public AuthorModelDto readByNewsId(Long newsId) {
				return authorMapper.authorToAuthorDto(authorRepository.findAllByNewsModelId(newsId));
		}
}
