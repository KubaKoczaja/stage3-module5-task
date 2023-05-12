package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentModelDto implements BaseEntityDto<Long>{
		private Long id;
		private String content;
		private LocalDateTime created;
		private LocalDateTime modified;
}
