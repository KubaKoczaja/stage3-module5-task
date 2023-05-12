package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentModelDto;
import com.mjc.school.service.dto.CommentRequestDto;
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
@RequestMapping(value = "/comments", consumes = {"application/JSON"}, produces = {"application/JSON"})
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting comments in the application")
public class CommentController implements BaseRestController<CommentRequestDto, CommentModelDto, Long> {
		private final CommentService commentService;
		@Override
		@GetMapping
		@ApiOperation(value = "View all comments", response = List.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved all comments"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<List<CommentModelDto>> readAllPagedAndSorted(@Min(1) @RequestParam int page,
																																	 @RequestParam(required = false, defaultValue = "10") int size,
																																	 @RequestParam(name = "sort_by", required = false, defaultValue = "content::asc") String sortBy) {
				return new ResponseEntity<>(commentService.readAllPagedAndSorted(page, size, sortBy), HttpStatus.OK);
		}

		@Override
		@GetMapping("/{id}")
		@ApiOperation(value = "View comment with specific if", response = CommentModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved comment by id"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<CommentModelDto> readById(@PathVariable Long id) {
				return new ResponseEntity<>(commentService.readById(id), HttpStatus.OK);
		}

		@Override
		@PostMapping("/create")
		@ResponseStatus(HttpStatus.CREATED)
		@ApiOperation(value = "Create a comment", response = CommentModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 201, message = "Successfully created a comment"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<CommentModelDto> create(@Valid @RequestBody CommentRequestDto createRequest) {
				return new ResponseEntity<>(commentService.create(createRequest), HttpStatus.CREATED);
		}

		@Override
		@PutMapping("/update/{id}")
		@ApiOperation(value = "Update comment's information", response = CommentModelDto.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully updated comment's information"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<CommentModelDto> update(@PathVariable Long id, @Valid @RequestBody CommentRequestDto updateRequest) {
				updateRequest.setId(id);
				return new ResponseEntity<>(commentService.update(updateRequest), HttpStatus.ACCEPTED);
		}

		@Override
		@DeleteMapping("/delete/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		@ApiOperation(value = "Deletes specific comment with the supplied id")
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully deletes the specific comment"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		}		)
		public void deleteById(@PathVariable Long id) {
				commentService.deleteById(id);
		}

		@GetMapping("/by-news/{newsId}")
		@ApiOperation(value = "View comments of news with supplied id", response = List.class)
		@ApiResponses(value = {
						@ApiResponse(code = 200, message = "Successfully retrieved comments by news id"),
						@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
						@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
						@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
						@ApiResponse(code = 500, message = "Application failed to process the request")
		})
		public ResponseEntity<List<CommentModelDto>> readByNewsId(@PathVariable Long newsId) {
				return new ResponseEntity<>(commentService.readByNewsId(newsId), HttpStatus.OK);
		}
}
