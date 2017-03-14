package com.stevengantz.springboot;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stevengantz.config.AuthenticationFileHandler;
import com.stevengantz.config.ConfigurationFileHandler;

/**
 * Created by Steven Gantz on 2/15/2017.
 *
 * HTTP Request handler
 */
@RestController
public class ConfigurationController {
	
	/**
	 * Spring Boot logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * GET method that returns the current JSON configuration
	 * @return ResponseEntity that contains the current configuration and HTTP/200
	 */
	 @CrossOrigin(origins = "*")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ResponseEntity<String> getConfig(HttpServletRequest request) {
        try {
        	logger.info("Client retrieved configuration from " + request.getRemoteAddr() + " using " + request.getHeader("user-agent"));
			return new ResponseEntity<>(new ConfigurationFileHandler().getConfigurationFileAsString(), HttpStatus.OK);
		} catch (IOException e) {
			logger.error("Configuration file wasn't found or couldn't be written to..." + "[" + this.getClass().getName() + "]");
			return new ResponseEntity<>("Some error has occurred, configuration file wasn't found.", HttpStatus.NO_CONTENT);
		}
    }
    
    /**
     * POST method that recieves the updated configuration
     * @param json JSON to be written as the current JSON configuration
     * @return ResponseEntity that contains the updated configuration and HTTP/200
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody String json) {
    	return new ResponseEntity<>(new ConfigurationFileHandler().writeConfigurationFile(json), HttpStatus.OK);
    }
    
    /**
     * GET method that returns whether auth was configured or not
     * @return ResponseEntity that HTTP/200 or /302 depending on circumstances
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/security", method = RequestMethod.GET)
    public ResponseEntity<String> checkAuthSetup() {
		try {
			AuthenticationFileHandler handler = new AuthenticationFileHandler();
			if(new File(handler.getAuthFileLocation()).length() == 0){
				return new ResponseEntity<>("Authentication Server Found, Please Add Content", HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>("Authentication Server Found", HttpStatus.OK);
			}
		} catch (IOException e) { // If exception thrown, serve no content w/ error
			return new ResponseEntity<>("Failure to access server file system", HttpStatus.SERVICE_UNAVAILABLE);
		}
    }
    
    /**
     * POST method that adds input into auth file
     * @return ResponseEntity that contains successful or failure depending on write result
     * @throws NoSuchAlgorithmException thrown if SHA-256 is not available through MessageDigest
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/setup-security", method = RequestMethod.POST)
    public ResponseEntity<String> writeAuth(@RequestBody String input) throws NoSuchAlgorithmException {
    	try {
			AuthenticationFileHandler localHandler = new AuthenticationFileHandler();
			localHandler.writeToFileWithString(input);
			return new ResponseEntity<>("Credentials Written", HttpStatus.OK);
		} catch (IOException e) { // If exception thrown, serve no content w/ error
			return new ResponseEntity<>("Failure to access server file system", HttpStatus.SERVICE_UNAVAILABLE);
		}
    }
    
    /**
     * POST method that returns whether credentials match or not
     * @return ResponseEntity that contains successful or failure depending on matching result
     * @throws NoSuchAlgorithmException thrown is SHA-256 is not available
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/security", method = RequestMethod.POST)
    public ResponseEntity<Boolean> validateAuth(@RequestBody String input) throws NoSuchAlgorithmException {
    	try {
    		AuthenticationFileHandler localHandler = new AuthenticationFileHandler();
    		if(localHandler.compareAgainstFile(input)){
    			return new ResponseEntity<>(true, HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(false, HttpStatus.OK);
    		}
    	} catch (IOException e) { // If exception thrown, serve no content w/ error
			return new ResponseEntity<>(false, HttpStatus.SERVICE_UNAVAILABLE);
		}
    }
}
