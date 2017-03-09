package com.stevengantz.springboot;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.stevengantz.config.AuthenticationFileHandler;
import com.stevengantz.config.ConfigurationFileHandler;

/**
 * Listener that runs methods on initial startup of the Spring Boot process
 * Created by Steven Gantz on 2/24/2017.
 * 
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>{
	
	/**
	 * Spring Boot logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Class name to be appended onto logger
	 */
	private final String className = this.getClass().getName();
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {
			new ConfigurationFileHandler().getConfigurationFile();
			new AuthenticationFileHandler();
		} catch (IOException e) {
			throw new RuntimeException("Can't build/find auth file... exiting...");
		}
		logger.info("Refreshing Context" + "[" + className + "]");
	}

}
