package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRequestDto {
		private Long id;
		@Size(min = 5, max = 255)
		private String content;
		private LocalDateTime created;
		private LocalDateTime modified;
		private Long newsId;
}
