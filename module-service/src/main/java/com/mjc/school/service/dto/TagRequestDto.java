package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TagRequestDto implements BaseEntityDto<Long> {
		private Long id;
		@Size(min = 5, max = 255)
		private String name;
}
