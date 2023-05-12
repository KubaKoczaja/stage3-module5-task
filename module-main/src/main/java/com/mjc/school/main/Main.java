package com.mjc.school.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {"com.mjc.school.controller", "com.mjc.school.service"})
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.mjc.school.repository")
@EntityScan("com.mjc.school.model")
public class Main {
		public static void main(String[] args) {
				SpringApplication.run(Main.class, args);
		}
}