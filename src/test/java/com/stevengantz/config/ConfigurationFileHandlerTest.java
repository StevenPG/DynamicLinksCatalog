package com.stevengantz.config;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stevengantz.config.ConfigurationFileHandler;

public class ConfigurationFileHandlerTest {

	// File held for each unit test
	File tmp;
	
	@Before
	public void setUp() throws Exception {
		// Clear existing file
		tmp = new File("dynamiclinks/config/config.json");
		Files.deleteIfExists(tmp.toPath());
	}

	@After
	public void tearDown() throws Exception {
		Files.deleteIfExists(tmp.toPath());
	}

	@Test
	public void testGetConfigurationFile() throws IOException {
		ConfigurationFileHandler handler = new ConfigurationFileHandler();
		// getConfig file builds file if not exist, so it must be called first for test to pass
		assertTrue(handler.getConfigurationFile().toString().equals(handler.configFile.toString()));
	}

	@Test
	public void testGetConfigurationFileAsString() throws IOException {
		ConfigurationFileHandler handler = new ConfigurationFileHandler();
		// getConfigurationFileAsString builds file if not exist, so it must be called first for test to pass
		String methodResult = handler.getConfigurationFileAsString();
		byte[] encoded = Files.readAllBytes(Paths.get(handler.configFile.toURI()));
		String fileAsString = new String(encoded);
		assertTrue(methodResult.equals(fileAsString));
	}

	@Test
	public void testBuildOrRetrieveFile() throws IOException {
		// Build and immediately clear existing file
		ConfigurationFileHandler handler = new ConfigurationFileHandler();
		Files.deleteIfExists(tmp.toPath());
		
		handler.buildDirectory();
		handler.buildOrRetrieveFile();
		
		assertTrue(Files.exists(tmp.toPath()));
	}

	@Test
	public void testBuildDirectory() throws IOException {
		// Build and immediately clear existing file
		ConfigurationFileHandler handler = new ConfigurationFileHandler();
		Files.deleteIfExists(tmp.toPath());
		
		handler.buildDirectory();
		
		assertTrue(Files.exists(new File("dynamiclinks/config/").toPath()));
	}

	@Test
	public void testIsJSONFileValid() throws IOException {
		// Build config file, check for validity internally
		ConfigurationFileHandler handler = new ConfigurationFileHandler();
		handler.getConfigurationFile();
		assertTrue(handler.isJSONFileValid());
	}

	@Test
	public void testFillFileWithDefault() throws IOException {
		String defaultFileContent = "{" + 
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
		// Since file doesn't exist, defaultFileContent will be used internally
		ConfigurationFileHandler handler = new ConfigurationFileHandler();
		
		// Convert both strings to character arrays, sort and compare (JSON is not ordered)
		// Strip arrays of whitespace aswell (JSON spaces don't matter)
		char[] defaultAsArray = defaultFileContent.replaceAll(" ", "").toCharArray();
		char[] methodResultAsArray = handler.getConfigurationFileAsString().replaceAll(" ", "").toCharArray();
		
		// Sort both arrays
		Arrays.sort(defaultAsArray);
		Arrays.sort(methodResultAsArray);
		
		assertTrue(Arrays.equals(defaultAsArray, methodResultAsArray));
	}

}
