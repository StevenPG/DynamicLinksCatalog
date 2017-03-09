package com.stevengantz.config;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.stevengantz.json.JSONConfigurationPOJO;
import com.stevengantz.json.JSONReaderWriter;

/**
 * Class that defines standard operations on the configuration file located in the filesystem
 * that is written to, and read from this application.
 * Created by Steven Gantz on 2/24/2017.
 * 
 */
@Configuration
public class ConfigurationFileHandler {

	/**
	 * Abstract path minus current directory the
	 * configuration file will live
	 */
	protected String directory = "dynamiclinks/config";
	
	/**
	 * Name of configuration file
	 */
	protected String filename = "config.json";
	
	/**
	 * File pointer to actual configuration file that can be queried
	 */
	protected File configFile;
	
	/**
	 * Spring Boot logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Class name to be appended onto logger
	 */
	private final String className = this.getClass().getName();
	
	/**
     * Initial JSON string for application
     */
    private final String defaultFileContent = "{" + 
    "\"config\": {" + 
    	"\"PageTitle\": \"Dynamic Links Catalog\"," +
    	"\"PageFavicon\": \"https://pbs.twimg.com/profile_images/571295883073843200/OerZFKD_.png\"," +
		"\"DirectoryTitle\": \"ExampleTitle\"," + 
		"\"DirectoryHexColor\": \"#000000\"," + 
		"\"PageBackgroundURL\": \"/img/default_background.jpg\"," + 
		"\"cards\": [{" + 
			"\"Header\": \"ExampleHeader\"," + 
			"\"HeaderSubtitle\": \"ExampleSubtitle\"," + 
			"\"ExpandedText\": \"ExampleTextBody\"," + 
			"\"Image\": \"https://upload.wikimedia.org/wikipedia/commons/8/84/Example.svg\"," + 
			"\"Buttons\": [{" + 
				"\"buttontext\": \"ExampleButtonText\"," + 
				"\"linkURL\": \"\"" + 
					"}, {" + 
					"\"buttontext\": \"ExampleButtonText2\"," + 
					"\"linkURL\": \"\"" + 
						"}]" + 
						"}]" +
						"}" + 
						"}";

	
	/**
	 * Default constructor does nothing
	 */
	public ConfigurationFileHandler() {	
		
	}
	
	/**
	 * Retrieve file handle
	 * if it doesn't exist, recreate it to avoid errors!
	 * @return
	 */
	public File getConfigurationFile(){
		if(this.configFile != null && this.configFile.exists()){
			return configFile;
		} else {
			try {
				buildDirectory();
				buildOrRetrieveFile();
				
				// Only overwrite file if JSON config is bad
				if(!isJSONFileValid()){
					fillFileWithDefault();
				}
				
			} catch (IOException e) {
				logger.error("Configuration failed to write to file..." + "[" + className + "]");
				e.printStackTrace();
				System.exit(1);
			}
			return configFile;
		}
	}
	
	/**
	 * Write an incoming string into the configuration file after marshaling with Jackson
	 * @return string representation of file
	 */
	public String writeConfigurationFile(String json) {
		if(this.configFile != null && this.configFile.exists()){
			try {
				JSONConfigurationPOJO pojo = JSONReaderWriter.read(json);
				JSONReaderWriter.write(this.configFile, pojo);
				return JSONReaderWriter.readAsString(this.configFile);
			} catch (IOException e) {
				logger.error("Failed to read incoming POSTed json");
				e.printStackTrace();
			}
		} else {
			try {
				buildDirectory();
				buildOrRetrieveFile();
				
				// Only overwrite file if JSON config is bad
				if(!isJSONFileValid()){
					fillFileWithDefault();
				}
				
				JSONConfigurationPOJO pojo = JSONReaderWriter.read(json);
				JSONReaderWriter.write(this.configFile, pojo);
				return JSONReaderWriter.readAsString(this.configFile);
			} catch (IOException e) {
				logger.error("Failed to read incoming POSTed json");
				e.printStackTrace();
			}
			return null;
		}
		return json;
	}
	
	/**
	 * Retrieve file as string
	 * If file was damaged, recreate it before converting to string
	 * @return config file as string
	 * @throws IOException thrown when attempting to read/write from/to file
	 */
	public String getConfigurationFileAsString() throws IOException{
		if(this.configFile != null && this.configFile.exists()){
			return JSONReaderWriter.readAsString(configFile);
		} else {
			try {
				buildDirectory();
				buildOrRetrieveFile();
				
				// Only overwrite file if JSON config is bad
				if(!isJSONFileValid()){
					fillFileWithDefault();
				}
				
			} catch (IOException e) {
				logger.error("Configuration failed to write to file..." + "[" + className + "]");
				e.printStackTrace();
				System.exit(1);
			}
			return JSONReaderWriter.readAsString(configFile);
		}
	}
	
	/**
	 * Creates new file, or sets config to existing file
	 * @throws IOException if file cannot be created
	 */
	protected void buildOrRetrieveFile() throws IOException {
		File config = new File(this.directory + "/" + this.filename);
		logger.info("Resolving file: " + this.filename + " located in " + this.directory + "[" + className + "]");
		logger.info("Creating/Retrieving configuration file"+ "[" + className + "]");
		config.createNewFile();
		this.configFile = config;
	}
	
	/**
	 * Create directory structure
	 */
	protected void buildDirectory(){
		File configDirectory = new File(this.directory);
		logger.info("Building/Finding config directories" + "[" + className + "]");
		configDirectory.mkdirs();
	}
	
	/**
	 * Validate JSON to overwrite, if JSON is invalid, overwrite with default configuration...
	 * Configuration variable is linked to value at this point
	 */
	protected boolean isJSONFileValid(){
		try {
			JSONReaderWriter.read(this.configFile);
		} catch (IOException e) {
			logger.info("Data in configuration file was invalid... Overwriting with default" + "[" + className + "]");
			return false;
		}
		return true;
	}
	
	/**
	 * If file is created, fill with default JSON configuration
	 */
	protected void fillFileWithDefault(){
		try {
			JSONReaderWriter.write(this.configFile, JSONReaderWriter.read(this.defaultFileContent));
			logger.info("Wrote default configuration into new configuration file" + "[" + className + "]");
		} catch (JsonGenerationException e) {
			logger.error("Failed to generate JSON for default configuration");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.error("Failed to map JSON into default configuration");
			e.printStackTrace();
		} catch (JsonParseException e) {
			logger.error("Failed to parse JSON into default configuration");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Failed to write JSON into default configuration file");
			e.printStackTrace();
		}
	}
	
}
