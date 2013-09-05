package eu.nets.crazycrow.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;


public class CrazyCrowCustomer implements CrazyCrowCustomerMBean {

    private Status status = Status.ENABLED;

	private String inputEncoding = "ISO-8859-1";
	private String outputEncoding = "UTF-8";
	private String incomingDirectory = "crazycrow-incoming";
	private String destinationDirectory = "incoming";
    
	@Override
	public void changeEncodingAndMove() {
		for (File file : new File(incomingDirectory).listFiles()) {
			
			File tempFile = new File(file.getAbsolutePath() + "_cc_tmp");
			File finalDestination = new File(new File(destinationDirectory), file.getName());
			
			try {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file), inputEncoding);
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tempFile));			
			
				CharStreams.copy(reader, writer);
				
				reader.close();
				writer.flush();
				writer.close();

				file.delete();
				Files.move(tempFile, finalDestination);
				
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
