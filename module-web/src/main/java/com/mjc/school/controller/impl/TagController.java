package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagModelDto;
import com.mjc.school.service.dto.TagRequestDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tags", consumes = {"application/JSON"}, produces = {"application/JSON"})
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting tags in the application")
public class TagController implements BaseRestController<TagRequestDto, TagModelDto, Long> {
		private final TagService tagService;
		@Override
		@GetMapping
		@ApiOperation(value = "View all tags", response = List.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved all tags"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<List<TagModelDto>> readAllPagedAndSorted(@Min(1) @RequestParam int page,
																												@RequestParam(required = false, defaultValue = "10") int size,
																												@RequestParam(name = "sort_by", required = false, defaultValue = "name::asc") String sortBy) {

				return new ResponseEntity<>(tagService.readAllPagedAndSorted(page, size, sortBy), HttpStatus.OK);
		}

		@Override
		@GetMapping("/{id}")
		@ApiOperation(value = "View tag with specific if", response = TagModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved tag by id"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<TagModelDto> readById(@PathVariable Long id) {
				return new ResponseEntity<>(tagService.readById(id), HttpStatus.OK);
		}

		@Override
		@PostMapping("/create")
		@ResponseStatus(HttpStatus.CREATED)
		@ApiOperation(value = "Create a tag", response = TagModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 201, message = "Successfully created a tag"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<TagModelDto> create(@Valid @RequestBody TagRequestDto createRequest) {
				return new ResponseEntity<>(tagService.create(createRequest), HttpStatus.CREATED);
		}

		@Override
		@PutMapping("/update/{id}")
		@ApiOperation(value = "Update tag's information", response = TagModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully updated tag's information"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<TagModelDto> update(@PathVariable Long id, @Valid @RequestBody TagRequestDto updateRequest) {
				updateRequest.setId(id);
				return new ResponseEntity<>(tagService.update(updateRequest), HttpStatus.ACCEPTED);
		}

		@Override
		@DeleteMapping("/delete/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		@ApiOperation(value = "Deletes specific tag with the supplied id")
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully deletes the specific tag"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public void deleteById(@PathVariable Long id) {
				tagService.deleteById(id);
		}

		@GetMapping("/by-news/{newsId}")
		@ApiOperation(value = "View tags of news with supplied id", response = List.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved tags by news id"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<List<TagModelDto>> readByNewsId(@PathVariable Long newsId) {
				return new ResponseEntity<>(tagService.readByNewsId(newsId), HttpStatus.OK);
		}
}
