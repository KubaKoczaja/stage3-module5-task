package com.mjc.school.service.implementation;

import com.mjc.school.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Aspect
public class DeleteAuthorAspect {
		private final NewsRepository newsModelRepository;
		@Before("@annotation(OnDelete)")
		public void deletingNewsCreatedByDeletedAuthor(JoinPoint joinPoint) {
				Long deletedAuthorId = (Long) joinPoint.getArgs()[0];
				newsModelRepository.findAll()
								.stream()
								.filter(n -> n.getAuthorModel().getId().equals(deletedAuthorId))
								.forEach(n -> newsModelRepository.deleteById(n.getId()));
		}
}
