package com.mjc.school.repository;

import com.mjc.school.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
		@Query("select c from CommentModel c join FETCH c.newsModel n where n.id =:newsId")
		List<CommentModel> findAllByNewsModelId(@Param(value = "newsId") Long newsId);
}
