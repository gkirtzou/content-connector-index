package eu.openminted.content.index;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

import eu.openminted.content.index.IndexConfiguration;
import eu.openminted.content.index.IndexConnection;
import eu.openminted.content.index.MyJestResultHandler;
import eu.openminted.content.index.entities.Publication;
import eu.openminted.content.index.entities.PublicationGenerator;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IndexConfigTest.class})
public class IndexPublicationTest {

	private static final Logger log = LoggerFactory.getLogger(IndexPublicationTest.class);
	
	@Autowired
    private IndexConfiguration esConfig;
	
	@Autowired
	private IndexConnection esConnection;
	
	@Autowired
	private IndexPublication index;
/*	
	@Test
	public void createIndex() throws Exception {
		
		String indexName = "test_index2";
		log.info("TEST CREATE INDEX");
		log.info("Creating index " + indexName);
		JestResult result =  index.createIndex(indexName);
		log.info("Response code " + result.getResponseCode() );
		log.info("Succeded? " + result.isSucceeded());
		assert(result.isSucceeded() == true);
	}

	@Test
	@Ignore
    public void deleteIndex() throws Exception {
		
		log.info("TEST DELETE INDEX");
		log.info("Delete index " + esConfig.getIndex());
		JestResult result = index.existsIndex();
		if (result.isSucceeded()) {
			result =  index.deleteIndex(esConfig.getIndex());
			log.info("Response code " + result.getResponseCode() );
			log.info("Succeded? " + result.isSucceeded());
			assert(result.isSucceeded() == true);
		}
		else {
			log.info("Index " + esConfig.getIndex() + " does not exist");
			
		}
	}
	
	
	@Test
	@Ignore
	public void addIndexMapping() {

		
		log.info("TEST ADD MAPPING TO INDEX");
		
		
		String mapping =  "{ \" " + esConfig.getDocumentType() + " \" : { \"properties\" : { " +
							" \"openaireId\" : {\"type\" : \"string\", \"store\" : \"yes\"}, " +
							" \"hashValue\" : {\"type\" : \"string\", \"store\" : \"yes\"}, " +
							" \"mimeType\" : {\"type\" : \"string\", \"store\" : \"yes\"}, " +
							" \"pathToFile\" : {\"type\" : \"string\", \"store\" : \"yes\"} " +
							" } } }";
		PutMapping putMapping = new PutMapping.Builder(
		        esConfig.getIndex(),
		        esConfig.getDocumentType(),
		        mapping
		).build();
		
		log.info(putMapping.toString());
		log.info(mapping);
		JestResult result = client.execute(putMapping);
		log.info("Result::" + result.getJsonString());	
				
	}
	*/
	
	@Test	
	public void addObjectsToIndex() throws Exception {
			
		log.info("TEST ADD OBJECT TO INDEX");
		String pathToPdf = "/home/gkirtzou/Desktop/tmp/pdfs/";
		String urlDomain = "http://adonis.athenarc.gr/pdfs/";	
		PublicationGenerator pubGenerator = new PublicationGenerator(pathToPdf, urlDomain, 9);
		
		// Add publications
		for (int i = 0; i < 10; i++) {
			// Create publication                		  
			Publication doc = pubGenerator.generatePublication();
			String id = doc.getOpenaireId();
			log.info("Add publication :: " + doc.toString());
			
			// Add publication to Elastic Search
			boolean success = index.addObject(doc, id); 
			log.info("Succeded? " + success);
			assert(success == true);
		}
				
	}
	
	
	@Test
	public void getObjectsFromIndex() throws Exception {
		
		log.info("TEST GET OBJECT FROM INDEX");
		for (int i = 0; i < 10; i++) {
			String id = Integer.toString(i);
			log.info("Get Object " + id  + " of type " + esConfig.getDocumentType() + " from " + esConfig.getIndex());
			Publication pub = (Publication) index.getObject(id);
			log.info("Publication " + pub.toString());
		}
	}
		
		
	@Test
	public void containsObject() throws IOException {
		
		log.info("TEST CONTAINS OBJECT");
		String id = "1";
		boolean contains = index.containsObject(id);
		log.info("Object " + id  + " should exist in " + esConfig.getIndex() + ". Contains? " + contains);
		assert(contains == true);
		
		id = "10000";
		contains = index.containsObject(id);
		log.info("Object " + id  + " should not exist in " + esConfig.getIndex() + ". Contains? " + contains);
		assert(contains == false);
	
	}
	
}

