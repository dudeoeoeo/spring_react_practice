package com.cos.book.web;

import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;

import lombok.RequiredArgsConstructor;

// 스프링이 IoC 컨테이너에 Bean 들을 올릴 때(객체 생성자의 메모리가 올라감) 받기 위해서
// 변수에 final 을 붙이면 이 어노테이션에 의하여 생성자가 자동으로 만들어짐
@RequiredArgsConstructor 
@RestController
public class BookController {
	
	private final BookService bookService;
	
	// security (라이브러리 적용) - CORS 정책을 가지고 있음 (시큐리티가 CORS를 해제)
	@CrossOrigin
	@PostMapping("/book")
	public ResponseEntity<?> save(@RequestBody Book book) {
		System.out.printf("title: %s, author: %s \n", book.getTitle(), book.getAuthor());
		return new ResponseEntity<>(bookService.저장하기(book), HttpStatus.CREATED); // 200
	}
	
	@CrossOrigin
	@GetMapping("/book")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(bookService.모두가져오기(), HttpStatus.OK); // 200
	}
	
	@CrossOrigin
	@GetMapping("/book/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		System.out.println(" 한건가져오기 book get 매핑: "+id);
		return new ResponseEntity<>(bookService.한건가져오기(id), HttpStatus.OK); // 200
	}

	@CrossOrigin
	@PutMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
		System.out.println("update 매핑: "+id);
		System.out.printf("update 내용: %s, %s", book.getAuthor(), book.getTitle());
		return new ResponseEntity<>(bookService.수정하기(id, book), HttpStatus.OK); // 200
	}

	@CrossOrigin
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		System.out.println("요청 id: "+id);
		return new ResponseEntity<>(bookService.삭제하기(id), HttpStatus.OK); // 200
	}
}