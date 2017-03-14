package com.stevengantz.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Class that defines standard operations on the authentication file located in the filesystem
 * that is written to, and read from this application to validate settings updates.
 * Created by Steven Gantz on 2/24/2017.
 * 
 */
@Configuration
public class AuthenticationFileHandler {

	/**
	 * Abstract path minus current directory the
	 * security file will live
	 */
	protected String directory = "dynamiclinks/auth";
	
	/**
	 * Name of security file
	 */
	protected String filename = "auth.secret";
	
	/**
	 * File pointer to actual security file that can be queried
	 */
	protected File authFile;
	
	/**
	 * Spring Boot logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Class name to be appended onto logger
	 */
	private final String className = this.getClass().getName();
	
	/**
	 * Default constructor creates file if it doesn't exist.
	 * 
	 * File starts as empty.
	 * @throws IOException thrown if file can't be created
	 */
	public AuthenticationFileHandler() throws IOException {	
		// If file exists, do nothing
		if(this.authFile != null && this.authFile.exists()){
			logger.info("Authentication file found" + "[" + className + "]");
		} else {
			// Create the file and throw exception if failure occurs
			buildDirectory();
			buildOrLoadFile();
		}
	}
	
	/**
	 * Writes incoming string as bytes into file
	 * @throws IOException thrown if unable to write into file
	 */
	public void writeToFile(byte[] incomingBytes) throws IOException {
		FileOutputStream fos = new FileOutputStream(this.authFile);
		fos.write(incomingBytes);
		fos.close();
	}
	
	/**
	 * Writes incoming string as bytes into file
	 * @throws IOException thrown if unable to write into file
	 */
	public void writeToFileWithString(String incomingString) throws IOException {
		FileOutputStream fos = new FileOutputStream(this.authFile);
		fos.write(incomingString.getBytes());
		fos.close();
	}
	
	/**
	 * Reads bytes from file and compares to incoming string
	 * @throws IOException thrown if unable to read bytes from file
	 */
	public boolean compareAgainstFile(String in) throws IOException {
		return in.equals(new String(getFileBytes()));
	}
	
	/**
	 * Retrieve contents of file as byte array
	 * @throws IOException thrown if reading bytes fails
	 */
	protected byte[] getFileBytes() throws IOException{
		return Files.readAllBytes(this.authFile.toPath());
	}
	
	/**
	 * Creates new file, or sets config to existing file
	 * @throws IOException if file cannot be created
	 */
	protected void buildOrLoadFile() throws IOException {
		File auth = new File(this.directory + "/" + this.filename);
		if(!auth.createNewFile()){
			logger.error("Found Existing Config file " + "[" + className + "]");
		}
		this.authFile = auth;
	}
	
	/**
	 * Create directory structure
	 */
	protected void buildDirectory(){
		File configDirectory = new File(this.directory);
		configDirectory.mkdirs();
	}
	
	/**
	 * Return the location of the auth file
	 * @return location of auth file as string
	 */
	public String getAuthFileLocation(){
		return this.authFile.getPath();
	}
	
	/**
	 * Returns whether the file has any content
	 * @return whether file has content
	 * @throws IOException thrown when reading files
	 */
	public boolean isFileEmpty() throws IOException{
		byte[] fileBytes = this.getFileBytes();
		return fileBytes.length == 0;
	}
	
}
