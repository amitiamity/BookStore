/**
 * 
 */
package com.bookstore.utility;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

/**
 * @author amitpatel
 *
 */
@Component
public class Utility {

	@Autowired
	LoadBalancerClient loadBalancer;

	public URI getServiceURL(String serviceId, String fallBackURL) {
		URI uri = null;
		try {
			ServiceInstance sInstance = loadBalancer.choose(serviceId);
			uri = sInstance.getUri();
		} catch (RuntimeException ex) {
			uri = URI.create(fallBackURL);
		}
		return uri;
	}
}
