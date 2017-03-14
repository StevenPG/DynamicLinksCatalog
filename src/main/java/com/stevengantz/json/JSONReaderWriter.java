package com.stevengantz.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This object marshals and writes JSON configuration from POJO to file/string
 * for easy manipulation. Created by Steven Gantz on 2/26/2017.
 * 
 */
public class JSONReaderWriter {
	
	/**
	 * Method that reads from JSON string into JSONConfigurationPOJO object
	 * @param json input string to build into JSONConfigurationPOJO object
	 * @return JSONConfigurationPOJO object containing values from input file
	 * @throws JsonParseException thrown when parsing json fails
	 * @throws JsonMappingException thrown when mapping json fails
	 * @throws IOException thrown when writing to string fails
	 */
	public static JSONConfigurationPOJO read(String json) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().readValue(json, JSONConfigurationPOJO.class);
	}
	
	/**
	 * Method that reads from JSON file into JSONConfigurationPOJO object
	 * @param json input file to build JSONConfigurationPOJO object from
	 * @return JSONConfigurationPOJO object containing values from input file
	 * @throws JsonParseException thrown when parsing json fails
	 * @throws JsonMappingException thrown when mapping json fails
	 * @throws IOException thrown when writing to file fails
	 */
	public static JSONConfigurationPOJO read(File json) throws JsonParseException, JsonMappingException, IOException{
		List<String> jsonLines = Files.readAllLines(json.toPath());
		
		StringBuilder jsonBuilder = new StringBuilder();
		
		for(String line : jsonLines){
			jsonBuilder.append(line);
		}
		String jsonString = jsonBuilder.toString();
		return new ObjectMapper().readValue(jsonString, JSONConfigurationPOJO.class);
	}
	
	public static String readAsString(File configFile) throws IOException{
		return new ObjectMapper().writeValueAsString(new ObjectMapper().readValue(configFile, JSONConfigurationPOJO.class));
	}
	
	/**
	 * Write a JSONConfigurationPOJO directly into a file by Jackson unmarshaling
	 * @param file file to write into
	 * @param pojo pojo to marshal into file
	 * @throws JsonGenerationException thrown when generating json fails
	 * @throws JsonMappingException thrown when mapping json into file fails
	 * @throws IOException thrown when writing into file fails
	 */
	public static void write(File file, JSONConfigurationPOJO pojo) throws JsonGenerationException, JsonMappingException, IOException{
		new ObjectMapper().writeValue(file, pojo);
	}
}
