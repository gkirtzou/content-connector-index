package eu.openminted.content.index;


import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.openminted.content.index.IndexConfiguration;
import eu.openminted.content.index.IndexConnection;
import eu.openminted.content.index.IndexHandler;
import eu.openminted.content.index.entities.Publication;

@Configuration
public class IndexConfigTest {
	
	@Bean
	public IndexConfiguration getEsConfig() throws IOException, Exception {
		return( IndexConfiguration.getInstance());
	}
	
	@Bean
	public IndexConnection getEsConnect() throws IOException, Exception {
		IndexConfiguration esConfig = getEsConfig();
		IndexConnection esConnect = new IndexConnection(esConfig.getHost(), esConfig.getPort());
        return esConnect;
	}
	
	@Bean
	public IndexPublication getIndex() throws IOException, Exception {
		IndexPublication index = new IndexPublication(getEsConfig());
		return index;    
	}

}