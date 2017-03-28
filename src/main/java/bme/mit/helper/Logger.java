package bme.mit.helper;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {

	private static Logger instance = null;
	private static final String PATHSTRING = System.getProperty("user.dir") + "\\src\\main\\resources\\log.txt";
	
	private Logger() {
		deleteFile();
	}
	
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		
		return instance;
	}
	
	public void logMessage(String message) {
		//String time = LocalDateTime.now().toString();
		StringBuilder messageBuilder = new StringBuilder("[INFO]: ");
		messageBuilder.append(message).append("\n");
		
	    write(messageBuilder.toString());
	}
	
	public void logError(String message) {
		//String time = LocalDateTime.now().toString();
		StringBuilder errorBuilder = new StringBuilder("[ERROR]: ");
		errorBuilder.append(message).append("\n");
		
	    write(errorBuilder.toString());
	}
	
	private void write(String message) {
		byte[] data = message.getBytes();
	    Path path = Paths.get(PATHSTRING);
	    
	    try (OutputStream out = new BufferedOutputStream(
	      Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
	      out.write(data, 0, data.length);
	    } catch (IOException x) {
	      System.err.println(x);
	    }
	}
	
	private void deleteFile() {
		try {
			Files.delete(Paths.get(PATHSTRING));
		} catch (NoSuchFileException e) {
			
		} catch (IOException e) {
			
		}
	}
}
