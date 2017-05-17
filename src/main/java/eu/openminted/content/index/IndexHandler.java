package eu.openminted.content.index;

import java.io.IOException;



import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.params.Parameters;


public class IndexHandler<T> {
	
	private IndexConnection indexConn;
	private JestClient client;
    	
	
	public IndexHandler(String host, String port) {
		this.indexConn = new IndexConnection(host, port);
		this.client = indexConn.getClient();
	}
	
	public JestResult createIndex(String indexName) throws IOException  {
		return this.client.execute(new CreateIndex.Builder(indexName).build());		
	}
	
	public JestResult deleteIndex(String indexName) throws IOException  {
		return this.client.execute(new DeleteIndex.Builder(indexName).build());
	}
	
	public JestResult existsIndex(String indexName) throws IOException {
		return this.client.execute(new IndicesExists.Builder(indexName).build());
	}
	
	public JestResult addMapping(String indexName, String documentType, String mapping) throws IOException {
		PutMapping putMapping = new PutMapping.Builder(
		        indexName,
		        documentType,
		        mapping		       
		).build();
		JestResult result = client.execute(putMapping);
		return result;
	}

	public boolean containsObject(String indexName, String documentType, String id) throws IOException {
		Get get = new Get.Builder(indexName, id).type(documentType).build();
		JestResult result = this.client.execute(get);		
		return (result.isSucceeded());		
	}

	public JestResult addObject(String indexName, String documentType, T object, String id) throws IOException  {
		Index index = new Index.Builder(object).index(indexName).type(documentType).id(id).build();
   	   	JestResult result = client.execute(index);
   	   	return result;
	}	
	
	public void addAsyncObject(String indexName, String documentType, T object, String id) throws IOException  {
		Index index = new Index.Builder(object).index(indexName).type(documentType).id(id).build();
   	   	client.executeAsync(index, new MyJestResultHandler());
   	   	return;
	}	
	
	public JestResult getObject(String indexName, String documentType, String id) throws IOException {
		Get get = new Get.Builder(indexName, id).type(documentType).build();
		JestResult result = this.client.execute(get);
		return result;
	}

	public SearchResult searchObject(String indexName, String documentType, String query) throws IOException {
		Search search = new Search.Builder(query)
				// multiple index or types can be added.
				.addIndex(indexName)
				.addType(documentType)
				.setParameter(Parameters.SIZE, 100)
				.build();
		
		SearchResult resultSearch = client.execute(search);		
		return resultSearch;
	}
	
	
}
