package com.mjc.school.repository;

import com.mjc.school.model.NewsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsModel, Long> {
		@Query("Select n from NewsModel n join n.tagModelSet t where t.name like %:tagName%")
		List<NewsModel> findAllByTagModelName(@Param(value = "tagName") String tagName);
		@Query("Select n from NewsModel n join n.tagModelSet t where t.id like %:tagId%")
		List<NewsModel> findAllByTagModelId(@Param(value = "tagId") Long tagId);
		@Query("Select n from NewsModel n join n.authorModel a where a.name = :authorName")
		List<NewsModel> findAllByAuthorModelName(@Param(value = "authorName") String authorName);
		@Query("Select n from NewsModel n where n.title like %:title%")
		List<NewsModel> findAllByTitle(@Param(value = "title") String title);
		@Query("Select n from NewsModel n where n.content like %:content%")
		List<NewsModel> findAllByContent(@Param(value = "content") String content);
}
