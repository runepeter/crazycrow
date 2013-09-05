package eu.nets.crazycrow.fs;

import eu.nets.crazycrow.Crow;

public interface CrazyCrowCustomerMBean extends Crow {

	String getIncomingDirectory();
	
	void setIncomingDirectory(String incomingDirectory);
	
	String getDestinationDirectory();
	
	void setDestinationDirectory(String destinationDirectory);
	
	String getOutputEncoding();
	
	void setOutputEncoding(String encoding);
	
	String getInputEncoding();
	
    void setInputEncoding(String encoding);

    void send();
    
    void changeEncoding();
    
    void cutFile();
    
}
