package com.mjc.school.repository;

import com.mjc.school.model.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorModel, Long> {
		@Query("Select a FROM AuthorModel a JOIN a.newsModelList n WHERE n.id = :newsId")
		AuthorModel findAllByNewsModelId(@Param(value = "newsId") Long newsId);
}
