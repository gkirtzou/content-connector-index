package eu.openminted.content.index;


import java.io.IOException;

import eu.openminted.content.index.entities.Publication;
import io.searchbox.client.JestResult;

public class IndexPublication {

	private IndexHandler<Publication> index;
	private IndexConfiguration indexConfig;
	
	public IndexPublication(IndexConfiguration indexConfig) {
		this.indexConfig = indexConfig;
		this.index = new IndexHandler<Publication>(indexConfig.getHost(),indexConfig.getPort());
	}
	
	public boolean addObject(Publication object, String id) throws IOException {
		JestResult result =  this.index.addObject(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), object, id);
		return (result.isSucceeded());
	}
	
	public Publication getObject(String id) throws IOException {
		JestResult result = this.index.getObject(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), id);
		Publication pub = result.getSourceAsObject(Publication.class);
		return pub;
	}
	
	public boolean containsObject(String id) throws IOException  {
		return this.index.containsObject(this.indexConfig.getIndex(), this.indexConfig.getDocumentType(), id);
	}
	
	
	
	
}
