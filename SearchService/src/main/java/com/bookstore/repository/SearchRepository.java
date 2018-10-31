/**
 * 
 */
package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.model.Book;

/**
 * @author amitpatel
 *
 */
@Repository
public interface SearchRepository  extends JpaRepository<Book, Long>{

	@Query("SELECT b FROM Book b where b.author LIKE %:author%")
	List<Book> findByAuthorIgnoreCase(@Param("author")String author);
	List<Book> findByIsbnIgnoreCase(String isbn);
	@Query("SELECT b FROM Book b where b.title LIKE %:title%")
	List<Book> findByTitleIgnoreCase(@Param("title")String title);
}
