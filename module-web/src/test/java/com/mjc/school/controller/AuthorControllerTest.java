package com.mjc.school.controller;

import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorRequestDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorControllerTest {
		private AuthorService authorService;

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
						basePath = "/authors";
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
		@Disabled
		void shouldReturnStatusOkAfterReadAllWithParameters() {
				given().contentType(ContentType.JSON).when().get("?page=1&size=2").then().statusCode(200);
		}

		@Test
		@Order(2)
		@Disabled
		void shouldReturnOKStatusAndEntity() {
				given().contentType(ContentType.JSON).when().get("/1").then().statusCode(200);
		}

		@Test
		@Order(3)
		@Disabled
		void shouldCreateNewEntityAndReturnStatusCreated() {
				AuthorRequestDto authorRequestDto = new AuthorRequestDto(4L,"bleble", LocalDateTime.now(),LocalDateTime.now(),3L);
				given().contentType(ContentType.JSON).request().body(authorRequestDto).when().post("/create").then().statusCode(201);
		}

		@Test
		@Order(4)
		@Disabled
		void shouldUpdateEntityAndReturnStatusAccepted() {
				AuthorRequestDto authorRequestDto = new AuthorRequestDto(1L,"bleble", LocalDateTime.now(),LocalDateTime.now(),3L);
				given().contentType(ContentType.JSON).request().body(authorRequestDto).when().put("/update/1").then().statusCode(202);
		}

		@Test
		@Order(5)
		@Disabled
		void shouldDeleteEntityAndReturnStatusOk() {
				given().contentType(ContentType.JSON).when().delete("/delete/2").then().statusCode(204);
		}

		@Test
		@Order(6)
		@Disabled
		void readByNewsId() {
				given().contentType(ContentType.JSON).when().get("/by-news/1").then().statusCode(200);
		}
}