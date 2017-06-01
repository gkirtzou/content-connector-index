package eu.openminted.content.index;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.openminted.content.index.entities.Publication;
import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

/**
 * @author gkirtzou
 *
 */
public class IndexPublication {

	private IndexHandler<Publication> index;
	private IndexConfiguration indexConfig;
	
	public IndexPublication(IndexConfiguration indexConfig) {
		this.indexConfig = indexConfig;
		this.index = new IndexHandler<Publication>(indexConfig.getHost(),indexConfig.getPort());
	}
	
	public boolean createIndex() throws IOException {
		JestResult result = this.index.createIndex(this.indexConfig.getIndex());
		return result.isSucceeded();
	}
	
	public boolean existsIndex() throws IOException {
		JestResult result = this.index.existsIndex(this.indexConfig.getIndex());
		return result.isSucceeded();	
	}
	
	
	public boolean deleteIndex() throws IOException {
		JestResult result = this.index.deleteIndex(this.indexConfig.getIndex());
		return result.isSucceeded();
	}
	
	public boolean addMapping() throws IOException {
		JestResult result = this.index.addMapping(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), this.indexConfig.getMapping());
		return result.isSucceeded();
	}
	
	public boolean addPublication(Publication object, String id) throws IOException {
		JestResult result =  this.index.addObject(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), object, id);
		return (result.isSucceeded());
	}
	
	public void addAsyncPublication(Publication object, String id) throws IOException {
		this.index.addAsyncObject(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), object, id);
		return;
	}
	
	public Publication getPublication(String id) throws IOException {
		JestResult result = this.index.getObject(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), id);
		Publication pub = result.getSourceAsObject(Publication.class);
		return pub;
	}
	
	public boolean containsPublication(String id) throws IOException  {
		return this.index.containsObject(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), id);
	}
	
	public ArrayList<Publication> searchObjects(String query) throws IOException {
		SearchResult result =  this.index.searchObject(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), query);
		ArrayList<Publication> listPub = null;
		
		if (result.isSucceeded()) {
			List<SearchResult.Hit<Publication, Void>> hits = result.getHits(Publication.class);
			listPub = new ArrayList<Publication>();
		
			Iterator<Hit<Publication, Void>> itPub = hits.iterator();	
			while (itPub.hasNext()) {
				Hit<Publication, Void> hit = itPub.next();			
				listPub.add(hit.source);				
			}
		}
		return listPub;
	}
	
	public void disconnect() {
		this.index.disconnect();
	}
	
	
}
