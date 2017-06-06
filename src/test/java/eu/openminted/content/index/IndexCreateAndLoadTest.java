package eu.openminted.content.index;


import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.openminted.content.index.IndexConfiguration;
import eu.openminted.content.index.IndexPublication;
import eu.openminted.content.index.entities.Publication;

/**
 * Creates an index and load it with data in json format.
 * @author gkirtzou
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IndexConfigTest.class})
public class IndexCreateAndLoadTest {

	private static final Logger log = LoggerFactory.getLogger(IndexCreateAndLoadTest.class);
	
	@Autowired
    private IndexConfiguration indexConfig;
	
	@Autowired
	private IndexPublication index;
	
	@Test
	@Ignore
	public void indexCreateAndBulkLoad() throws IOException {
		Properties configFile = new Properties();
		configFile.load(IndexCreateAndLoadTest.class.getClassLoader().getResourceAsStream("indexCreateAndLoadTestConfig.properties"));
		
		String pathToFiles = configFile.getProperty("pathToFiles");
		if (pathToFiles != null) {
			pathToFiles = pathToFiles.trim();
		} else {
			pathToFiles = "/media/pdfs/";
		}	   
		
		log.info("Creating test index " + indexConfig.getIndex());
		boolean success =  index.createIndex();
		log.info("Succeded? " + success);
		//assert(success);
		log.info("Add documents from json");
		ObjectMapper mapper = new ObjectMapper();
		File folder = new File(pathToFiles + "metadata/");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			File workingFile = listOfFiles[i];
			if (workingFile.isFile()) {
				log.info("Loading data from file " + workingFile.getName());
				
				//JSON from file to Object
				Publication pub = mapper.readValue(workingFile, Publication.class);
				String id = pub.getOpenaireId();
				log.info("Add publication :: " + pub.toString());
				
				// Add publication to Elastic Search
				success = index.addPublication(pub, id); 
				log.info("Succeded? " + success);
				assert(success);		
			}
								
		}
		
		

	}

  
}
