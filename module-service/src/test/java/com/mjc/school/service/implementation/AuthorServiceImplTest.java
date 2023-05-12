package com.mjc.school.service.implementation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.model.AuthorModel;
import com.mjc.school.service.dto.AuthorModelDto;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.mapper.AuthorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
		@InjectMocks
		private AuthorServiceImpl authorService;
		@Mock
		private AuthorRepository authorRepository;
		@Mock
		private AuthorMapper authorMapper;

		@Test
		void shouldReadAllAuthors() {
				List<AuthorModel> authorModelList = List.of(new AuthorModel(), new AuthorModel());
				when(authorMapper.authorToAuthorDto(any(AuthorModel.class))).thenReturn(new AuthorModelDto());
				when(authorRepository.findAll()).thenReturn(authorModelList);
				int lengthExpected = 2;
				assertEquals(lengthExpected, authorService.readAll().size());
		}

		@Test
		void shouldReturnEntityWithGivenId() {
				AuthorModelDto expected = new AuthorModelDto(1L, "test", LocalDateTime.now(), LocalDateTime.now());
				when(authorRepository.findById(anyLong())).thenReturn(Optional.of(new AuthorModel()));
				when(authorMapper.authorToAuthorDto(any(AuthorModel.class))).thenReturn(expected);
				assertEquals(expected, authorService.readById(1L));
		}

		@Test
		void shouldReturnAddedObjectIfValuesAreCorrect() {
				AuthorRequestDto authorModelToCreate = new AuthorRequestDto(1L, "test", LocalDateTime.now(), LocalDateTime.now(), 1L);
				when(authorMapper.authorRequestToAuthor(any(AuthorRequestDto.class))).thenReturn(new AuthorModel());
				lenient().when(authorRepository.save(any(AuthorModel.class))).thenReturn(new AuthorModel());
				AuthorModelDto savedAuthorDto = new AuthorModelDto(1L, "test", LocalDateTime.now(), LocalDateTime.now());
				lenient().when(authorMapper.authorToAuthorDto(any(AuthorModel.class))).thenReturn(savedAuthorDto);
				assertEquals(savedAuthorDto, authorService.create(authorModelToCreate));
		}

		@Test
		void shouldUpdateNewsWithGivenIdWhenValuesOfTitleAndContentAreCorrect() {
				AuthorRequestDto authorModelToUpdate = new AuthorRequestDto(1L, "test", LocalDateTime.now(), LocalDateTime.now(), 1L);
				AuthorModelDto updatedAuthorDto = new AuthorModelDto(1L, "test", LocalDateTime.now(), LocalDateTime.now());
				when(authorRepository.findById(anyLong())).thenReturn(Optional.of(new AuthorModel()));
				lenient().when(authorMapper.authorRequestToAuthor(any(AuthorRequestDto.class))).thenReturn(new AuthorModel());
				when(authorRepository.save(any(AuthorModel.class))).thenReturn(new AuthorModel());
				lenient().when(authorMapper.authorToAuthorDto(any(AuthorModel.class))).thenReturn(updatedAuthorDto);
				assertEquals(updatedAuthorDto, authorService.update(authorModelToUpdate));
		}
}