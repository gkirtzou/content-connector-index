package eu.openminted.content.index;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


public class IndexConfiguration {

	private static IndexConfiguration instance = null;
	private static String host;
	private static String port;
	private static String index;
	private static String documentType;
	
	
	/**
	 * Class constructor
	 * @throws Exception 
	 * @throws IOException 
    */
    protected IndexConfiguration() throws IOException, Exception{
        getPropValues(); 
        
    }
    
    /**
     * Gets instance of the singleton class
     * @return 
     * @throws Exception 
     * @throws IOException 
     */
    public static IndexConfiguration getInstance() throws IOException, Exception{     
        if(instance == null){
            instance = new IndexConfiguration();
        }
        return instance;
    }

    /**
     * Gets property values from property file
     * @throws IOException, Exception 
     */
    private void getPropValues() throws IOException, Exception {
		Properties configFile = new Properties();
		configFile.load(IndexConfiguration.class.getClassLoader().getResourceAsStream("elasticSearchConfig.properties"));
				
		host = configFile.getProperty("host");
	    if (host != null) {
	    	host = host.trim();
	    } else {
	    	host = "localhost";
	    }	   
	    
		port = configFile.getProperty("port");
	    if (port != null) {
	    	port = port.trim();
	    } else {
	    	port = "9200";
	    }
	    
	    index = configFile.getProperty("index");
	    if (index != null) {
	    	index = index.trim();
	    } else {
	    	throw new Exception("index parameter does not exists");
	    } 
	    
		
		documentType =  configFile.getProperty("documentType");
		if (documentType != null) {
			documentType = documentType.trim();
		} else {
	    	throw new Exception("documentType parameter does not exists");
	    } 
	}


	public String getHost() {
		return host;
	}


	
	public String getPort() {
		return port;
	}


	public String getIndex() {
		return index;
	}


	public String getDocumentType() {
		return documentType;
	}



}
