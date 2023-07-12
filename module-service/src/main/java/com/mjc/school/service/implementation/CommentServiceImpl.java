package com.mjc.school.service.implementation;

import com.mjc.school.model.CommentModel;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentModelDto;
import com.mjc.school.service.dto.CommentRequestDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
		private final CommentRepository commentRepository;
		private final CommentMapper commentMapper;
		private final NewsRepository newsRepository;
		@Override
		public List<CommentModelDto> readAll() {
				return commentRepository.findAll().stream().map(commentMapper::commentToCommentDto).collect(Collectors.toList());
		}

		@Override
		public List<CommentModelDto> readAllPagedAndSorted(int page, int size, String sortBy) {
				String[] split = sortBy.split("::");
				return commentRepository
								.findAll(PageRequest
												.of(page - 1, size, split[1].equals("asc") ? Sort.by(split[0]).ascending() : Sort.by(split[0]).descending()))
								.getContent()
								.stream()
								.map(commentMapper::commentToCommentDto)
								.collect(Collectors.toList());
		}

		@Override
		public CommentModelDto readById(Long id) {
				return commentMapper.commentToCommentDto(commentRepository
								.findById(id)
								.orElseThrow(() -> new NoSuchEntityException("No comment with such id!")));
		}

		@Override
		public CommentModelDto create(CommentRequestDto createRequest) {
				createRequest.setCreated(LocalDateTime.now());
				createRequest.setModified(LocalDateTime.now());
				CommentModel commentToBeSaved = commentMapper.commentRequestToComment(createRequest);
				commentToBeSaved.setNewsModel(newsRepository.findById(createRequest.getNewsId()).orElseThrow(() -> new NoSuchEntityException("No news with such id!")));
				return commentMapper.commentToCommentDto(commentRepository.save(commentToBeSaved));
		}

		@Override
		public CommentModelDto update(CommentRequestDto updateRequest) {
				CommentModel commentToBeUpdated = commentRepository.findById(updateRequest.getId()).orElseThrow(() -> new NoSuchEntityException("No comment with such id!"));
				commentToBeUpdated.setContent(updateRequest.getContent());
				commentToBeUpdated.setModified(LocalDateTime.now());
				return commentMapper.commentToCommentDto(commentRepository.save(commentToBeUpdated));
		}

		@Override
		public boolean deleteById(Long id) {
				commentRepository.deleteById(id);
				return true;
		}

		@Override
		public List<CommentModelDto> readByNewsId(Long newsId) {
				return commentRepository.findAllByNewsModelId(newsId).stream().map(commentMapper::commentToCommentDto).collect(Collectors.toList());
		}
}
