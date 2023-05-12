package com.mjc.school.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString(exclude = {"tagModelSet", "authorModel", "commentModelList"})
@EqualsAndHashCode(exclude = {"id", "tagModelSet", "commentModelList"})
public class NewsModel implements BaseEntity<Long>{
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		@NonNull
		private String title;
		@NonNull
		private String content;
		@NonNull
		private LocalDateTime createDate;
		@NonNull
		private LocalDateTime lastUpdateDate;
		@ManyToOne(cascade={CascadeType.MERGE})
		@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "id")
		private AuthorModel authorModel;
		@OneToMany(mappedBy = "newsModel")
		private List<CommentModel> commentModelList = new ArrayList<>();
		@ManyToMany(cascade={CascadeType.MERGE})
		@JoinTable(name="NEWS_TAG",
						joinColumns=@JoinColumn(name="TAG_ID", referencedColumnName = "id"),
						inverseJoinColumns=@JoinColumn(name="NEWS_ID", referencedColumnName = "ID"))
		private Set<TagModel> tagModelSet = new HashSet<>();
}
