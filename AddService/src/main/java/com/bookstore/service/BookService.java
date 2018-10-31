/**
 * 
 */
package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.constants.Constant;
import com.bookstore.excpetions.InvalidRequestException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

/**
 * @author amitpatel
 *
 */
@Service
public class BookService implements IBookService{

	
	@Autowired
	BookRepository bookRepo;
	
	@Override
	public Book addBook(Book book) throws InvalidRequestException {
		if (validateBookInfo(book)) {
			return bookRepo.save(book);
		}else {
			throw new InvalidRequestException(Constant.INCOMPLETEBOOKINFO);
		}
	}
	
	private boolean validateBookInfo(Book book) {
		
		if (book.getAuthor() == null || book.getIsbn() == null 
					|| book.getTitle() == null || book.getPrice() == 0.0)
			return false;
		else
			return true;
	}

}
