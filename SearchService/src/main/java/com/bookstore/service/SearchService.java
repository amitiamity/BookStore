/**
 * 
 */
package com.bookstore.service;


import static com.bookstore.constants.Constant.AUTHOR;
import static com.bookstore.constants.Constant.BOOKID_OR_TITLE_MISSING;
import static com.bookstore.constants.Constant.BOOK_DOESNOT_EXIST;
import static com.bookstore.constants.Constant.EMPTY_STRING;
import static com.bookstore.constants.Constant.INVALID_SEARCH_KEY;
import static com.bookstore.constants.Constant.ISBN;
import static com.bookstore.constants.Constant.MEDIA_COVERAGE_URL;
import static com.bookstore.constants.Constant.TITLE;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bookstore.exceptions.InvalidSearchRequestException;
import com.bookstore.model.Book;
import com.bookstore.model.MediaPost;
import com.bookstore.repository.SearchRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author amitpatel
 *
 */
@Service
public class SearchService implements ISearchService {

	@Autowired
	SearchRepository searchRepo;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Optional<List<Book>> searchBook(String searchBy, String searchValue) throws InvalidSearchRequestException {
		searchBy = searchBy.toUpperCase();
		List<Book> result = null;
		if (checkValidKeys(searchBy)) {
			switch (searchBy) {
			case ISBN:
				result = searchRepo.findByIsbnIgnoreCase(searchValue);
				break;
			case TITLE:
				result = searchRepo.findByTitleIgnoreCase(searchValue);
				break;

			case AUTHOR:
				result = searchRepo.findByAuthorIgnoreCase(searchValue);
			}
		} else {
			throw new InvalidSearchRequestException(INVALID_SEARCH_KEY);
		}
		if (result == null || result.size() == 0)
			return Optional.empty();
		else
			return Optional.of(result);
	}

	private boolean checkValidKeys(String searchKey) {
		if (ISBN.equals(searchKey) || TITLE.equals(searchKey) || AUTHOR.equals(searchKey)) {
			return true;
		} else {
			return false;
		}
	}
	

	@HystrixCommand(fallbackMethod="getDefaultMediaPost")
	public Optional<List<String>> getMediaPost(long bookId) throws InvalidSearchRequestException{
		Optional<Book> book = searchRepo.findById(bookId);
		
		String bookTitle = book.map(Book::getTitle).orElse(BOOK_DOESNOT_EXIST);
		if (bookTitle.equals(EMPTY_STRING) || bookTitle.equals(BOOK_DOESNOT_EXIST)) {
			throw new InvalidSearchRequestException(BOOKID_OR_TITLE_MISSING);
		}else {
			MediaPost[] posts =  restTemplate.getForObject(MEDIA_COVERAGE_URL,MediaPost[].class);
			Predicate<MediaPost> byTitle = post -> post.getTitle()
					.filter(title-> title.toLowerCase().contains(bookTitle.toLowerCase())).isPresent();
			
			Predicate<MediaPost> byBody = post->post.getBody().filter(
						body -> body.toLowerCase().contains(bookTitle.toLowerCase())).isPresent();
			
			List<String> matchingMediaTitles =
						Stream.of(posts)
							.filter(byTitle.or(byBody))
								.map(post->post.getTitle())
									.flatMap(o->o.isPresent()?Stream.of(o.get()):Stream.empty())
										.collect(Collectors.toList());

			return Optional.of(matchingMediaTitles);
		}
		
	}
	
	public Optional<List<String>> getDefaultMediaPost(long bookId){
		return Optional.of(Arrays.asList(""));
	}
}
