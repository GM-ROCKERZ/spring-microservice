package com.gpl3194.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gpl3194.microservices.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitConfigurationController {

	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")

	public LimitConfiguration retrieveLimitsFromConfigurations() {
		
		return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
		
	}
	
	@GetMapping("/fault-tolerance-example")

	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
	public LimitConfiguration retrieveFromConfigurations() {
		
		throw new RuntimeException("Not available");
		
	}
	
public LimitConfiguration fallbackRetrieveConfiguration() {
		
		return new LimitConfiguration(999,9);
		
	}
}
