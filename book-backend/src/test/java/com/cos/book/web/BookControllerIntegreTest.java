package com.cos.book.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/*
 	통합 테스트 (모든 Bean들을 똑같이 IoC 올리고 테스트 하는 것)
 	모든 Bean을 등록
 	WebEnvironment.MOCK = 실제 톰켓을 올리는게 아니라, 다른 톰켓으로 테스트
 	WebEnvironment.RANDOM_POR = 실제 톰켓으로 테스트
 	@AutoConfigureMockMvc MockMvc를 IoC에 등록해줌
 	@Transactional은 각각의 테스트함수가 종료될 때마다 트랜잭션을 rollback 해주는 어노테이션
 */
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) 
public class BookControllerIntegreTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@BeforeEach
	public void init() {
		//entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE book AUTO_INCREMENT = 1").executeUpdate();
	}
	
	// BDDMockito 패턴  given, when, then
	@Test
	public void save_테스트() throws Exception {
		// given (테스트를 하기 위한 준비)
		Book book = new Book(null, "스프링 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book);
		// when(bookService.저장하기(book)).thenReturn(new Book(1L, "스프링 따라하기", "코스"));
		
		// when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then (검증)
		resultAction
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.title").value("스프링 따라하기"))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findAll_테스트() throws Exception {
		// given
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "스프링부트 따라하기", "코스"));
		books.add(new Book(null, "리액트 따라하기", "코스"));
		// IntegreTest 는 실제 DB를 테스트하기 때문에 현재 h2 DB에는 데이터가 없음
		// 그렇다면 실제 데이터를 저장하고 그 저장한 데이터를 던져주고
		// 테스트가 끝나면 @Transactional 어노테이션에 의해서 rollback
		bookRepository.saveAll(books);
		
		// when
		ResultActions resultAction = mockMvc.perform(get("/book")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		//then
		resultAction
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", Matchers.hasSize(2)))
		.andExpect(jsonPath("$.[1].title").value("리액트 따라하기"))
		.andDo(MockMvcResultHandlers.print());
				
	}
	
	
	@Test
	public void findById_테스트() throws Exception {
		// given
		Long id = 2L;
		
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "스프링부트 따라하기", "코스"));
		books.add(new Book(null, "리액트 따라하기", "코스"));
		books.add(new Book(null, "JUnit 따라하기", "코스"));
		bookRepository.saveAll(books);
		
		
		// when
		ResultActions resultAction = mockMvc.perform(get("/book/{id}", id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
		.andExpect(status().isOk())
		//.andExpect(jsonPath("$", Matchers.hasSize(1)))
		.andExpect(jsonPath("$.title").value("리액트 따라하기"))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void update_테스트() throws Exception {
		// given
		Long id = 2L;
		
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "스프링부트 따라하기", "코스"));
		books.add(new Book(null, "리액트 따라하기", "코스"));
		books.add(new Book(null, "JUnit 따라하기", "코스"));
		bookRepository.saveAll(books);
		
		Book book = new Book(null, "테스트 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book);
		
		// when
		ResultActions resultAction = mockMvc.perform(put("/book/{id}",id)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title").value("테스트 따라하기"))
		.andDo(MockMvcResultHandlers.print());
	}
}
