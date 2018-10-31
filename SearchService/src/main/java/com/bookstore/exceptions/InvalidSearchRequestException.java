/**
 * 
 */
package com.bookstore.exceptions;

/**
 * @author amitpatel
 *
 */
public class InvalidSearchRequestException extends Exception{
	
	
	private static final long serialVersionUID = 1L;

	public InvalidSearchRequestException(String msg) {
		super(msg);
	}

}
