/**
 * 
 */
package com.bookstore.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.exceptions.InvalidRequestException;
import com.bookstore.model.Book;
import com.bookstore.service.OrderService;

/**
 * @author amitpatel
 *
 */
@RestController
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@PutMapping("/books/{id}/buy")
	public ResponseEntity<?> buyBook(@PathVariable("id") Long bookId){
		try {
			Optional<Book> Opbook = orderService.buyBook(bookId);
			return new ResponseEntity<Book>(Opbook.get(),HttpStatus.OK);
		} catch (InvalidRequestException e) {
			return new ResponseEntity<InvalidRequestException>(e,HttpStatus.NOT_FOUND);
		}
	}
}
