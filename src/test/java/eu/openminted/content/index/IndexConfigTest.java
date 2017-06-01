package eu.openminted.content.index;


import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.openminted.content.index.IndexConfiguration;
import eu.openminted.content.index.IndexConnection;

/**
 * @author gkirtzou
 *
 */
@Configuration
public class IndexConfigTest {
	
	@Bean
	public IndexConfiguration getEsConfig() throws IOException, Exception {
		return( IndexConfiguration.getInstance());
	}
		
	@Bean
	public IndexPublication getIndex() throws IOException, Exception {
		IndexPublication index = new IndexPublication(getEsConfig());
		return index;    
	}

}