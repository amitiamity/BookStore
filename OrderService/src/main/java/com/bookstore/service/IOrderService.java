/**
 * 
 */
package com.bookstore.service;

import java.util.Optional;

import com.bookstore.exceptions.InvalidRequestException;
import com.bookstore.model.Book;

/**
 * @author amitpatel
 *
 */
public interface IOrderService {
	
	public Optional<Book> buyBook(Long bookId) throws InvalidRequestException;

}
