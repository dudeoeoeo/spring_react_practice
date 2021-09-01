package com.cos.book.domain;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.BookRepository;

// 단위 테스트 ( DB 관련된 Bean이 IoC에 등록되면 됨 )

@Transactional
@AutoConfigureTestDatabase(replace = Replace.ANY) // Replace.ANY 가짜 디비로 테스트, Replace.NONE 실제 DB로 테스트
@DataJpaTest // JPA 관련 애들만 IoC에 띄워짐
public class BookRepositoryUnitTest {

	@Autowired
	private BookRepository bookRepository;
}
