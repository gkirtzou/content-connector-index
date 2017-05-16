package eu.openminted.content.index;

import java.io.IOException;
import java.util.Properties;

public class IndexConfiguration {

	private static IndexConfiguration instance = null;
	private String host;
	private String port;
	private String index;
	private String documentType;
	private String mapping;
	
	
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
		configFile.load(IndexConfiguration.class.getClassLoader().getResourceAsStream("indexConfig.properties"));
				
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
		
		mapping = configFile.getProperty("mapping");
		if ( mapping != null) {
			mapping = mapping.trim();
		}
		
	}


	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	
	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	
	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	
	public String getDocumentType() {
		return this.documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getMapping() {
		return this.mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}




}
