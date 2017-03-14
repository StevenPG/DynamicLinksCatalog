package com.stevengantz.config;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationFileHandlerTest {

	// File held for each unit test
	File tmp;
	
	@Before
	public void setUp() throws Exception {
		// Clear existing file
		tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());
	}

	@After
	public void tearDown() throws Exception {
		Files.deleteIfExists(tmp.toPath());
	}
	
	@Test
	public void testAuthenticationFileHandler() throws IOException {
		new AuthenticationFileHandler();
		assertTrue(tmp.exists());
	}
	
	@Test
	public void testGetAuthFileLocation() throws IOException {
		String path = "dynamiclinks/auth/auth.secret";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		
		// If on a windows system, replace \ with /
		assertTrue(path.equals(authH.getAuthFileLocation().replaceAll("\\\\", "/")));
	}

	@Test
	public void testWriteToFile() throws IOException {
		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		authH.writeToFile(testString.getBytes());

		assertTrue(testString.equals(new String(Files.readAllBytes(tmp.toPath()))));
	}
	
	@Test
	public void testWriteToFileWithString() throws IOException {

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		authH.writeToFileWithString(testString);

		assertTrue(testString.equals(new String(Files.readAllBytes(tmp.toPath()))));
	}

	@Test
	public void testCompareAgainstFile() throws IOException {

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		authH.writeToFile(testString.getBytes());

		assertTrue(authH.compareAgainstFile(testString));
	}

	@Test
	public void testCompareAgainstFileManualWrite() throws IOException {

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();

		FileOutputStream fos = new FileOutputStream(tmp);
		fos.write(testString.getBytes());
		fos.close();

		assertTrue(authH.compareAgainstFile(testString));
	}

	@Test
	public void testGetFileBytes() throws IOException {

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		
		// Write bytes
		FileOutputStream fos = new FileOutputStream(tmp);
		fos.write(testString.getBytes());
		fos.close();
		
		// Read bytes manually
		byte [] fileBytes = Files.readAllBytes(tmp.toPath());
		
		assertTrue(Arrays.equals(fileBytes, authH.getFileBytes()));
	}

	@Test
	public void testBuildOrLoadFile() throws IOException {
		// Build and immediately clear existing file
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		Files.deleteIfExists(tmp.toPath());

		authH.buildDirectory();
		authH.buildOrLoadFile();
		
		assertTrue(Files.exists(tmp.toPath()));
	}

	@Test
	public void testBuildDirectory() throws IOException {
		// Build and immediately clear existing file
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		Files.deleteIfExists(tmp.toPath());
		
		authH.buildDirectory();

		assertTrue(Files.exists(new File("dynamiclinks/auth/").toPath()));
	}
	
	@Test
	public void testIsFileEmpty() throws IOException {
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		assertTrue(authH.getFileBytes().length == 0);
	}

}
