/**
 * 
 */
package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import com.bookstore.exceptions.InvalidSearchRequestException;
import com.bookstore.model.Book;

/**
 * @author amitpatel
 *
 */
public interface ISearchService {

	public Optional<List<Book>> searchBook(String searchBy, String searchValue) throws InvalidSearchRequestException;
	public Optional<List<String>> getMediaPost(long bookId) throws InvalidSearchRequestException;
}
