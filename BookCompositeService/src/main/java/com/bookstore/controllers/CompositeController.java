/**
 * 
 */
package com.bookstore.controllers;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bookstore.core.model.Book;
import com.bookstore.exceptions.RestTemplateErrorHandler;
import com.bookstore.utility.Constant;
import com.bookstore.utility.Utility;

/**
 * @author amitpatel
 *
 */
@RestController
public class CompositeController {
	
	@Autowired
	public CompositeController(RestTemplateBuilder restBuilder,
			RestTemplateErrorHandler errorHandler) {
		this.restTemplate = restBuilder.errorHandler(new RestTemplateErrorHandler()).build();
	}
	
	public CompositeController() {}

	@Autowired
	Utility utility;

	RestTemplate restTemplate;
	
	@PostMapping("/books")
	public ResponseEntity<?> addBook(@RequestBody Book book) {

		URI uri = utility.getServiceURL(Constant.ADD_SERVICE_ID, Constant.FALLBACK_ADD_SERVICE_URL);
		HttpEntity<Book> postBody = new HttpEntity<Book>(book);
		String url = uri.toString() + "/books";
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, postBody,Object.class);
		return response;
	}
	
	@GetMapping("/books")
	public ResponseEntity<?> searchBook(@RequestParam Map<String,String> queryMap) {
		String searchBy = queryMap.keySet().stream().findFirst().orElse("all");
		String searchValue = queryMap.get(searchBy);
		URI uri = utility.getServiceURL(Constant.SEARCH_SERVICE_ID, Constant.FALLBACK_SEARCH_SERVICE_URL);
		String url = uri.toString() + "/books?"+searchBy+"="+searchValue;
		ResponseEntity<?> response = restTemplate.getForEntity(url,Object.class);
		return response;
	}
	
	@GetMapping("/books/{id}/posts")
	public ResponseEntity<?> searchMediaPosts(@PathVariable("id") Long bookId) {
		URI uri = utility.getServiceURL(Constant.SEARCH_SERVICE_ID, Constant.FALLBACK_SEARCH_SERVICE_URL);
		String url = uri.toString() + "/books/"+bookId+"/posts/";
		ResponseEntity<?> response = restTemplate.getForEntity(url,Object.class);
		return response;
	}
	
	@PutMapping("/books/{id}/buy")
	public ResponseEntity<?> orderBook(@PathVariable("id") Long bookId) {
		URI uri = utility.getServiceURL(Constant.ORDER_SERVICE_ID, Constant.FALLBACK_ORDER_SERVICE_URL);
		String url = uri.toString() + "/books/"+bookId+"/buy/";
		ResponseEntity<?> response = restTemplate.exchange(url,HttpMethod.PUT,HttpEntity.EMPTY,Object.class);
		return response;	
	}
	

}
