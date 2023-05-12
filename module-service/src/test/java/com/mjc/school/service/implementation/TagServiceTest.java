package com.mjc.school.service.implementation;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.model.TagModel;
import com.mjc.school.service.dto.TagModelDto;
import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
  @InjectMocks
  private TagServiceImpl tagService;
  @Mock
  private TagRepository tagRepository;
  @Mock
  private TagMapper tagMapper;

		@Test
		void shouldReadAllTags() {
      List<TagModel> tagModelList = List.of(new TagModel(), new TagModel());
      when(tagMapper.tagToTagDTO(any(TagModel.class))).thenReturn(new TagModelDto());
      when(tagRepository.findAll()).thenReturn(tagModelList);
      assertEquals(tagModelList.size(), tagService.readAll().size());
		}

		@Test
		void shouldReturnTagById() {
      TagModel tagModel = new TagModel(1L, "test", new HashSet<>());
      TagModelDto expectedDto = new TagModelDto(1L, "test");
      when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tagModel));
      when(tagMapper.tagToTagDTO(any(TagModel.class))).thenReturn(expectedDto);
      assertEquals(expectedDto, tagService.readById(1L));
		}

		@Test
		void shouldCreateTag() {
      TagModelDto expectedDto = new TagModelDto(1L, "test");
      TagRequestDto requestDto = new TagRequestDto(1L, "test");
      when(tagMapper.tagRequestToTag(any(TagRequestDto.class))).thenReturn(new TagModel());
      when(tagRepository.save(any(TagModel.class))).thenReturn(new TagModel());
      when(tagMapper.tagToTagDTO(any(TagModel.class))).thenReturn(expectedDto);
      assertEquals(expectedDto, tagService.create(requestDto));
		}

		@Test
		void shouldUpdateTagUsingGivenRequest() {
      TagModelDto expectedDto = new TagModelDto(1L, "test");
      TagRequestDto requestDto = new TagRequestDto(1L, "test");
      TagModel tagModel = new TagModel(1L, "test", new HashSet<>());
      when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tagModel));
      when(tagRepository.save(any(TagModel.class))).thenReturn(new TagModel());
      lenient().when(tagMapper.tagToTagDTO(any(TagModel.class))).thenReturn(expectedDto);
      assertEquals(expectedDto, tagService.update(requestDto));
		}

		@Test
		void readByNewsId() {
      List<TagModel> tagModelList = List.of(new TagModel(), new TagModel());
      lenient().when(tagMapper.tagToTagDTO(any(TagModel.class))).thenReturn(new TagModelDto());
      when(tagRepository.findAllByNewsModelId(anyLong())).thenReturn(tagModelList);
      assertEquals(tagModelList.size(), tagService.readByNewsId(1L).size());
		}
}