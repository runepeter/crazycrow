package eu.nets.crazycrow.fs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;


public class CrazyCrowCustomer implements CrazyCrowCustomerMBean {

    private Status status = Status.ENABLED;

	private String inputEncoding = "ISO-8859-1";
	private String outputEncoding = "UTF-8";
	private String incomingDirectory = "crazycrow-incoming";
	private String destinationDirectory = "incoming";
    
    @Override
    public void send() {
    	for (File originalFile : new File(incomingDirectory).listFiles()) {
			File finalDestination = new File(new File(destinationDirectory), originalFile.getName());

			try {
				Files.move(originalFile, finalDestination);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}			
		}
    }
    
	@Override
	public void cutFile() {
		for (File originalFile : new File(incomingDirectory).listFiles()) {
			
			int cutOff = new Random(System.currentTimeMillis()).nextInt((int)originalFile.length());
			File tempFile = new File(originalFile.getAbsolutePath() + "_cc_tmp");
			
			try {
				InputStream inputStream = new  FileInputStream(originalFile);
				OutputStream outputStream = new FileOutputStream(tempFile);			
			
				for (int i = 0; i < cutOff; i++) {
					outputStream.write(inputStream.read());
				}
				
				inputStream.close();
				outputStream.flush();
				outputStream.close();
				
				originalFile.delete();
				Files.move(tempFile, originalFile);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}			
			
			
		}
	}
	
	@Override
	public void changeEncodingSomeLines() {
		for (File originalFile : new File(incomingDirectory).listFiles()) {
			
			int numberOfLines;
			
			try {
				List<String> lines = Files.readLines(originalFile, Charset.forName(this.inputEncoding));
				numberOfLines = lines.size();
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
			
			int changeLineNumber = new Random(System.currentTimeMillis()).nextInt(numberOfLines);
			int lineCounter = 0;
			
			File tempFile = new File(originalFile.getAbsolutePath() + "_cc_tmp");
			
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(originalFile), inputEncoding));
				OutputStream outputStream = new FileOutputStream(tempFile);
				
				String line = null;
				while ((line = reader.readLine()) != null) {
					line += "\n";
					if (lineCounter == changeLineNumber) {
						outputStream.write(line.getBytes(outputEncoding));
					} else {
						outputStream.write(line.getBytes(inputEncoding));
					}
					
					lineCounter++;
				}
				
				reader.close();
				outputStream.flush();
				outputStream.close();
				
				originalFile.delete();
				Files.move(tempFile, originalFile);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		}
	}
	
	@Override
	public void changeEncoding() {
		for (File originalFile : new File(incomingDirectory).listFiles()) {
			
			File tempFile = new File(originalFile.getAbsolutePath() + "_cc_tmp");
			
			try {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(originalFile), inputEncoding);
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile), outputEncoding);			
			
				CharStreams.copy(reader, writer);
				
				reader.close();
				writer.flush();
				writer.close();

				originalFile.delete();
				Files.move(tempFile, originalFile);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		}
		
	}
	
	@Override
	public String getIncomingDirectory() {
		return incomingDirectory;
	}

	@Override
	public void setIncomingDirectory(String incomingDirectory) {
		this.incomingDirectory = incomingDirectory;
	}

	@Override
	public String getDestinationDirectory() {
		return destinationDirectory;
	}

	@Override
	public void setDestinationDirectory(String destinationDirectory) {
		this.destinationDirectory = destinationDirectory;
	}
	
	@Override
	public String getOutputEncoding() {
		return outputEncoding;
	}
	
    @Override
	public void setOutputEncoding(String encoding) {
		this.outputEncoding = encoding;
	}

    @Override
    public String getInputEncoding() {
    	return inputEncoding;
    }
    
	@Override
	public void setInputEncoding(String encoding) {
		this.inputEncoding = encoding;
	}
    
	@Override
	public String getDomain() {
		return "eu.nets.crazycrow";
	}

	@Override
	public String getName() {
		return "Customer";
	}

	@Override
	public String getType() {
		return CrazyCrowCustomer.class.getSimpleName();
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public boolean isEnabled() {
		return Status.ENABLED == status;
	}

	@Override
	public boolean enable() {
		this.status = Status.ENABLED;
        return true;
	}

	@Override
	public boolean disable() {
		this.status = Status.DISABLED;
        return true;
	}

}
