/**
 * 
 */
package com.bookstore.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.exceptions.InvalidSearchRequestException;
import com.bookstore.model.Book;
import com.bookstore.model.MediaPost;
import com.bookstore.service.SearchService;

/**
 * Controller to handle the search operation
 * 
 * @author amitpatel
 *
 */
@RestController
public class SearchBookController {

	@Autowired
	SearchService searchService;

	@GetMapping("/books")
	public ResponseEntity<?> searchBook(@RequestParam Map<String, String> queryMap) {

		String searchBy = queryMap.keySet().stream().findFirst().orElse("all");
		String searchValue = queryMap.get(searchBy);
		try {
			Optional<List<Book>> books = searchService.searchBook(searchBy, searchValue);
			if (books.isPresent()) {
				return new ResponseEntity<List<Book>>(books.get(), HttpStatus.FOUND);
			} else {
				return new ResponseEntity<InvalidSearchRequestException>
						(new InvalidSearchRequestException("Book Does not exist"),HttpStatus.NOT_FOUND);
			}
		} catch (InvalidSearchRequestException e) {
			return new ResponseEntity<InvalidSearchRequestException>(e,HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}
	
	@GetMapping("/books/{id}/posts")
	public ResponseEntity<?> getBookaMediaCoverage(@PathVariable("id") Long bookId){
		
		try {
			Optional<List<String>> titles = searchService.getMediaPost(bookId);
			if(titles.isPresent()) {
				return new ResponseEntity<List<String>>(titles.get(), HttpStatus.FOUND);
			}else {
				return new ResponseEntity<List<String>>(titles.get(), HttpStatus.NOT_FOUND);
			}
			
		} catch (InvalidSearchRequestException e) {
			return new ResponseEntity<InvalidSearchRequestException>(e,HttpStatus.NOT_FOUND);
		}
	}
}
