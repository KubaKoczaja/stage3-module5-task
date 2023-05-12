package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsModelDto;
import com.mjc.school.service.dto.NewsRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/news", consumes = {"application/JSON"}, produces = {"application/JSON"})
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting news in the application")
public class NewsController implements BaseRestController<NewsRequestDto, NewsModelDto, Long> {
		private final NewsService newsService;
		@Override
		@GetMapping
		@ApiOperation(value = "View all news", response = List.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved all news"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<List<NewsModelDto>> readAllPagedAndSorted(@Min(1) @RequestParam int page,
																																			 @RequestParam(required = false, defaultValue = "10") int size,
																																			 @RequestParam(name = "sort_by", required = false, defaultValue = "title::asc") String sortBy) {
				return new ResponseEntity<>(newsService.readAllPagedAndSorted(page, size, sortBy), HttpStatus.OK);
		}

		@Override
		@GetMapping("/{id}")
		@ApiOperation(value = "View news with specific if", response = NewsModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved news by id"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<NewsModelDto> readById(@PathVariable Long id) {
				return new ResponseEntity<>(newsService.readById(id), HttpStatus.OK);
		}

		@Override
		@PostMapping("/create")
		@ResponseStatus(HttpStatus.CREATED)
		@ApiOperation(value = "Create a news", response = NewsModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 201, message = "Successfully created a news"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<NewsModelDto> create(@Valid @RequestBody NewsRequestDto createRequest) {
				return new ResponseEntity<>(newsService.create(createRequest), HttpStatus.CREATED);
		}

		@Override
		@PutMapping("/update/{id}")
		@ApiOperation(value = "Update news information", response = NewsModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully updated news information"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<NewsModelDto> update(@PathVariable Long id, @Valid @RequestBody NewsRequestDto updateRequest) {
				updateRequest.setId(id);
				return new ResponseEntity<>(newsService.update(updateRequest), HttpStatus.ACCEPTED);
		}

		@Override
		@DeleteMapping("/delete/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		@ApiOperation(value = "Deletes specific news with the supplied id")
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully deletes the specific author"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public void deleteById(@PathVariable Long id) {
				newsService.deleteById(id);
		}

		@GetMapping("/by-various-parameters")
		@ApiOperation(value = "View news compliant to various parameters", response = Set.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved news by various parameters"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<Set<NewsModelDto>> readNewsByVariousParameters(@RequestBody NewsRequestDto newsRequestDto) {
				return new ResponseEntity<>(newsService.readNewsByVariousParameters(newsRequestDto), HttpStatus.OK);
		}
}