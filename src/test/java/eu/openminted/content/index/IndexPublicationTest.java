package eu.openminted.content.index;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.openminted.content.index.IndexConfiguration;
import eu.openminted.content.index.entities.Publication;
import eu.openminted.content.index.entities.PublicationGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IndexConfigTest.class})
public class IndexPublicationTest {

	private static final Logger log = LoggerFactory.getLogger(IndexPublicationTest.class);
	
	@Autowired
    private IndexConfiguration esConfig;
	
	@Autowired
	private IndexPublication index;
	
	
	
	
	private void createIndex() throws Exception {
		
		log.info("Creating test index " + esConfig.getIndex());
		boolean success =  index.createIndex();
		log.info("Succeded? " + success);
		assert(success);
	}

	public void deleteIndex() throws Exception {
		
		log.info("Deleting test index " + esConfig.getIndex());
		boolean success =  index.deleteIndex();
		log.info("Succeded? " + success);
		assert(success);
	}
	
	public void addMapping() throws Exception {
		log.info("Add the following map \n" + esConfig.getMapping() + "\n to index " + esConfig.getIndex());
		boolean success =  index.addMapping();
		log.info("Succeded? " + success);
		assert(success);
		
	}
	
	public void addPublicationsToIndex() throws Exception {
			
		log.info("Adding objects to index");
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
			boolean success = index.addPublication(doc, id); 
			log.info("Succeded? " + success);
			assert(success);
		}
				
	}
	
	
	public void getPublicationsFromIndex() throws Exception {
		
		log.info("Getting objects from index");
		for (int i = 3; i < 5; i++) {
			String id = Integer.toString(i);
			log.info("Get Object " + id  + " of type " + esConfig.getDocumentType() + " from " + esConfig.getIndex());
			Publication pub = index.getPublication(id);
			log.info("Publication " + pub.toString());
		}
	}
		
	public void containsPublication() throws IOException {
		
		log.info("Contains objects");
		String id = "1";
		boolean contains = index.containsPublication(id);
		log.info("Object " + id  + " should exist in " + esConfig.getIndex() + ". Contains? " + contains);
		assert(contains);
		
		id = "10000";
		contains = index.containsPublication(id);
		log.info("Object " + id  + " should not exist in " + esConfig.getIndex() + ". Contains? " + contains);
		assert(!contains);
	
	}
	
	@Test
	//@Ignore
	public void basicIndexOperations() throws Exception {
		log.info("BASIC INDEX OPERATIONS");
		createIndex();
		addMapping();
		addPublicationsToIndex();
		containsPublication();
		getPublicationsFromIndex();
		deleteIndex();
	}
	
	@Test
	@Ignore
	public void searchPublicationsByMimeType() throws Exception {
		log.info("SEARCHING PUBLICATION BY MIMETYPE");
		createIndex();
		addPublicationsToIndex();
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		log.info("Search Source builder completed");
		searchSourceBuilder.query(QueryBuilders.matchQuery("mimeType", "pdf"));
		String query = searchSourceBuilder.toString();
		log.info("Testing query\n" + query);
		
		ArrayList<Publication> pubs = index.searchObjects(query);
		assert(pubs != null);
		Iterator<Publication> itPubs = pubs.iterator();
		while (itPubs.hasNext()) {
			log.info(itPubs.next().toString());
		}		
		deleteIndex();		
	}
	
}

