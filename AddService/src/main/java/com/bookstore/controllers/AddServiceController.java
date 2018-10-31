/**
 * 
 */
package com.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.excpetions.InvalidRequestException;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;

/**
 * Rest controller to add the books in bookstore
 * 
 * @author amitpatel
 *
 */
@RestController
public class AddServiceController {

	@Autowired
	BookService bookService;

	@PostMapping("/books")
	public ResponseEntity<?> addBook(@RequestBody Book book) {
		Book savedBook = null;
		try {
			savedBook = bookService.addBook(book);
			return new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
		} catch (InvalidRequestException e) {
			return new ResponseEntity<InvalidRequestException>(e,HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
