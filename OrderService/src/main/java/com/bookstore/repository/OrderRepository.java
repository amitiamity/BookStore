/**
 * 
 */
package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.Book;

/**
 * @author amitpatel
 *
 */
public interface OrderRepository extends JpaRepository<Book, Long> {

	
}
