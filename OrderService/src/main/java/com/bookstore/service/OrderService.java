/**
 * 
 */
package com.bookstore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.exceptions.InvalidRequestException;
import com.bookstore.model.Book;
import com.bookstore.repository.OrderRepository;
import static com.bookstore.constants.Constant.NO_STOCK;

/**
 * @author amitpatel
 *
 */
@Service
public class OrderService implements IOrderService {

	
	@Autowired
	OrderRepository orderRepo;
	
	@Override
	public Optional<Book> buyBook(Long bookId) throws InvalidRequestException{
		Optional<Book> Opbook = orderRepo.findById(bookId);
		int count = Opbook.map(Book::getBookCount).orElse(0);
		if(count == 0) {
			throw new InvalidRequestException(NO_STOCK);
		}else {
			Book b = Opbook.get();
			b.setBookCount(--count);
			return Optional.of(orderRepo.save(b));
		}
		
	}

}
