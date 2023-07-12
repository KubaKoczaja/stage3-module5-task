package com.mjc.school.service.implementation;

import com.mjc.school.model.TagModel;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagModelDto;
import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
		private final TagRepository tagRepository;
		private final TagMapper tagMapper;

		@Override
		public List<TagModelDto> readAll() {
				return tagRepository.findAll().stream().map(tagMapper::tagToTagDTO).collect(Collectors.toList());
		}

		@Override
		public List<TagModelDto> readAllPagedAndSorted(int page, int size, String sortBy) {
				String[] split = sortBy.split("::");
				return tagRepository
								.findAll(PageRequest
												.of(page - 1, size, split[1].equals("asc") ? Sort.by(split[0]).ascending() : Sort.by(split[0]).descending()))
								.getContent()
								.stream()
								.map(tagMapper::tagToTagDTO)
								.collect(Collectors.toList());
		}
		@Override
		public TagModelDto readById(Long id) {
				return tagMapper
								.tagToTagDTO(tagRepository
												.findById(id)
												.orElseThrow(() -> new NoSuchEntityException("No news with such id!")));
		}

		@Override
		public TagModelDto create(TagRequestDto createRequest) {
				TagModel savedTag = tagMapper.tagRequestToTag(createRequest);
				return tagMapper.tagToTagDTO(tagRepository.save(savedTag));
		}

		@Override
		public TagModelDto update(TagRequestDto updateRequest) {
				TagModel tagFromDatabase = tagRepository.findById(updateRequest.getId())
								.orElseThrow(() -> new NoSuchEntityException("No such tag!"));
    		tagFromDatabase.setName(updateRequest.getName());
				return tagMapper.tagToTagDTO(tagRepository.save(tagFromDatabase));
		}

		@Override
		public boolean deleteById(Long id) {
				tagRepository.deleteById(id);
				return true;
		}

		@Override
		public List<TagModelDto> readByNewsId(Long newsId) {
				return tagRepository.findAllByNewsModelId(newsId).stream().map(tagMapper::tagToTagDTO).toList();
		}
}
