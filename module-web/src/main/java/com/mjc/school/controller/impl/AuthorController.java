package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorModelDto;
import com.mjc.school.service.dto.AuthorRequestDto;
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
@RequestMapping(value = "/authors", consumes = {"application/JSON"}, produces = {"application/JSON"})
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting authors in the application")
public class AuthorController implements BaseRestController<AuthorRequestDto, AuthorModelDto, Long> {
		private final AuthorService authorService;

		@Override
		@GetMapping
		@ApiOperation(value = "View all authors", response = List.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved all authors"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<List<AuthorModelDto>> readAllPagedAndSorted(@Min(1) @RequestParam int page,
																												@RequestParam(required = false, defaultValue = "10") int size,
																												@RequestParam(name = "sort_by", required = false, defaultValue = "name::asc") String sortBy) {

				return new ResponseEntity<>(authorService.readAllPagedAndSorted(page, size, sortBy), HttpStatus.OK);
		}
		@Override
		@GetMapping("/{id}")
		@ApiOperation(value = "View author with specific if", response = AuthorModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved author by id"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<AuthorModelDto> readById(@PathVariable Long id) {
				return new ResponseEntity<>(authorService.readById(id), HttpStatus.OK);
		}

		@Override
		@PostMapping("/create")
		@ResponseStatus(HttpStatus.CREATED)
		@ApiOperation(value = "Create an author", response = AuthorModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 201, message = "Successfully created an author"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<AuthorModelDto> create(@Valid @RequestBody AuthorRequestDto createRequest) {
				return new ResponseEntity<>(authorService.create(createRequest), HttpStatus.CREATED);
		}

		@Override
		@PutMapping("/update/{id}")
		@ApiOperation(value = "Update author's information", response = AuthorModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 202, message = "Successfully updated author's information"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<AuthorModelDto> update(@PathVariable Long id, @Valid @RequestBody AuthorRequestDto updateRequest) {
				updateRequest.setId(id);
				return new ResponseEntity<>(authorService.update(updateRequest), HttpStatus.ACCEPTED);
		}
		@PatchMapping("/patch/{id}")
		@ApiOperation(value = "Patch author's information", response = AuthorModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully patched author's information"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<AuthorModelDto> patch(@PathVariable Long id, @Valid @RequestBody AuthorRequestDto patchRequest) {
				patchRequest.setId(id);
				return new ResponseEntity<>(authorService.update(patchRequest), HttpStatus.ACCEPTED);
		}

		@Override
		@DeleteMapping("/delete/{id}")
		@ApiOperation(value = "Deletes specific author with the supplied id")
		@ApiResponses(value = {
						@ApiResponse(code = 204, message = "Successfully deletes the specific author"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteById(@PathVariable Long id) {
				authorService.deleteById(id);
		}

		@GetMapping("/by-news/{newsId}")
		@ApiOperation(value = "View author of news with supplied id", response = AuthorModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved author by news id"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<AuthorModelDto> readByNewsId(@PathVariable Long newsId) {
				return new ResponseEntity<>(authorService.readByNewsId(newsId), HttpStatus.OK );
	}
}
