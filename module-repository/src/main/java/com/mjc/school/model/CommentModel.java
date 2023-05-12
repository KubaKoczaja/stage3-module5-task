package com.mjc.school.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString(exclude = {"newsModel"})
@EqualsAndHashCode(exclude = {"id"})
public class CommentModel implements  BaseEntity<Long>{
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		@NonNull
		private String content;
		private LocalDateTime created;
		private LocalDateTime modified;
		@ManyToOne(cascade={CascadeType.MERGE})
		@JoinColumn(name = "NEWS_ID", referencedColumnName = "id")
		private NewsModel newsModel;

}
