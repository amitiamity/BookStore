/**
 * 
 */
package com.bookstore.exceptions;

/**
 * @author amitpatel
 *
 */
public class InvalidRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String msg) {
		super(msg);
	}
}