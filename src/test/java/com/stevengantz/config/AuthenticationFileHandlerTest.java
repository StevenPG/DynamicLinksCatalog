package com.stevengantz.config;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.Test;

public class AuthenticationFileHandlerTest {

	@Test
	public void testAuthenticationFileHandler() throws IOException {
		// Clear existing file
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());

		new AuthenticationFileHandler();

		// Delete file after
		assertTrue(tmp.exists());

		Files.deleteIfExists(tmp.toPath());
	}
	
	@Test
	public void testGetAuthFileLocation() throws IOException {
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());

		String path = "dynamiclinks/auth/auth.secret";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		
		// If on a windows system, replace \ with /
		assertTrue(path.equals(authH.getAuthFileLocation().replaceAll("\\\\", "/")));
	}

	@Test
	public void testWriteToFile() throws IOException {
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		authH.writeToFile(testString.getBytes());

		assertTrue(testString.equals(new String(Files.readAllBytes(tmp.toPath()))));
		Files.deleteIfExists(tmp.toPath());
	}
	
	@Test
	public void testWriteToFileWithString() throws IOException {
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		authH.writeToFileWithString(testString);

		assertTrue(testString.equals(new String(Files.readAllBytes(tmp.toPath()))));
		Files.deleteIfExists(tmp.toPath());
	}

	@Test
	public void testCompareAgainstFile() throws IOException {
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		authH.writeToFile(testString.getBytes());

		assertTrue(authH.compareAgainstFile(testString));
		Files.deleteIfExists(tmp.toPath());
	}

	@Test
	public void testCompareAgainstFileManualWrite() throws IOException {
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();

		FileOutputStream fos = new FileOutputStream(tmp);
		fos.write(testString.getBytes());
		fos.close();

		assertTrue(authH.compareAgainstFile(testString));
		Files.deleteIfExists(tmp.toPath());
	}

	@Test
	public void testGetFileBytes() throws IOException {
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());

		String testString = "TestString";
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		
		// Write bytes
		FileOutputStream fos = new FileOutputStream(tmp);
		fos.write(testString.getBytes());
		fos.close();
		
		// Read bytes manually
		byte [] fileBytes = Files.readAllBytes(tmp.toPath());
		
		assertTrue(Arrays.equals(fileBytes, authH.getFileBytes()));
		Files.deleteIfExists(tmp.toPath());
	}

	@Test
	public void testBuildOrLoadFile() throws IOException {
		// Build and immediately clear existing file
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());

		authH.buildDirectory();
		authH.buildOrLoadFile();
		
		// Delete file after
		assertTrue(Files.exists(tmp.toPath()));
		Files.deleteIfExists(tmp.toPath());
	}

	@Test
	public void testBuildDirectory() throws IOException {
		// Build and immediately clear existing file
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		File tmp = new File("dynamiclinks/auth/auth.secret");
		Files.deleteIfExists(tmp.toPath());
		
		authH.buildDirectory();

		// Delete file after
		assertTrue(Files.exists(new File("dynamiclinks/auth/").toPath()));
		Files.deleteIfExists(tmp.toPath());
	}
	
	@Test
	public void testIsFileEmpty() throws IOException {
		AuthenticationFileHandler authH = new AuthenticationFileHandler();
		assertTrue(authH.getFileBytes().length == 0);
	}

}
