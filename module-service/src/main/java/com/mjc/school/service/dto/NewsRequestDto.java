package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequestDto {
		private Long id;
		@Size(min = 5, max = 255)
		private String title;
		@Size(min = 5, max = 255)
		private String content;
		private LocalDateTime createDate;
		private LocalDateTime lastUpdateDate;
		private Long authorId;
		private String tagIds;
		private String tagNames;
		private String authorName;
}
