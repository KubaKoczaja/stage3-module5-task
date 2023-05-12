package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsRequestDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NewsControllerTest {
		@BeforeAll
		public static void setup() {
				String port = System.getProperty("server.port");
				if (port == null) {
						RestAssured.port = 8080;
				}
				else{
						RestAssured.port = Integer.parseInt(port);
				}


				String basePath = System.getProperty("server.base");
				if(basePath==null){
						basePath = "/news";
				}
				RestAssured.basePath = basePath;

				String baseHost = System.getProperty("server.host");
				if(baseHost==null){
						baseHost = "http://localhost";
				}
				RestAssured.baseURI = baseHost;
		}

		@Test
		@Order(1)
		void shouldReturnStatusOkAfterReadAllWithParameters() {
				given().contentType(ContentType.JSON).when().get("?page=1&size=2").then().statusCode(200);
		}

		@Test
		@Order(2)
		void shouldReturnOKStatusAndEntity() {
				given().contentType(ContentType.JSON).when().get("/1").then().statusCode(200);
		}

		@Test
		@Order(3)
		void shouldCreateNewEntityAndReturnStatusCreated() {
				NewsRequestDto newsRequestDto = new NewsRequestDto(4L,"bleble", "bleble",LocalDateTime.now(),LocalDateTime.now(),1L, "1", "test", "test");
				given().contentType(ContentType.JSON).request().body(newsRequestDto).when().post("/create").then().statusCode(201);
		}

		@Test
		@Order(4)
		void shouldUpdateEntityAndReturnStatusAccepted() {
				NewsRequestDto newsRequestDto = new NewsRequestDto(1L,"bleble", "bleble",LocalDateTime.now(),LocalDateTime.now(),1L, "1", "test", "test");
				given().contentType(ContentType.JSON).request().body(newsRequestDto).when().put("/update/1").then().statusCode(202);
		}

		@Test
		@Order(5)
		void shouldDeleteEntityAndReturnStatusOk() {
				given().contentType(ContentType.JSON).when().delete("/delete/2").then().statusCode(204);
		}
}