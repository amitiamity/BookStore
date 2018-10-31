/**
 * 
 */
package com.bookstore.service;

import com.bookstore.excpetions.InvalidRequestException;
import com.bookstore.model.Book;

/**
 * @author amitpatel
 *
 */
public interface IBookService {

	/**
	 * service to add book
	 * @param book
	 */
	public Book addBook(Book book) throws InvalidRequestException;
}
