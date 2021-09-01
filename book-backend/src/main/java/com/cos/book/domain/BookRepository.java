package com.cos.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;


// @Repository 를 적어야 스프링 IoC에 빈으로 등록이 되는데
// JpaRepository 를 extends하면 생략가능
// JpaRepository는 CRUD 함수를 들고 있음

public interface BookRepository extends JpaRepository<Book, Long>{ //Entity 클래스, PK 타입

}
