/**
 * 
 */
package com.bookstore.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * @author amitpatel
 *
 */
@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
				|| response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			System.out.println("Server Error => code : " 
					+ response.getStatusCode() + ", message : " + response.getStatusText());
		} else if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
			if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
				System.out.println("Not Found Error => code : " 
						+ response.getStatusCode() + ", message : " + response.getStatusText());
			}else if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
				System.out.println("Missing Data in input : " 
						+ response.getStatusCode() + ", message : " + response.getStatusText());
			}
		}

	}

}
