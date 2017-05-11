package eu.openminted.content.index;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import eu.openminted.content.index.IndexConfiguration;
import eu.openminted.content.index.IndexConnection;
import eu.openminted.content.index.entities.Publication;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.params.Parameters;

public class TestSearchDocumentFromIndexElasticSearch {

	public static void main(String[] args) throws Exception {
		
		IndexConfiguration esConfig = IndexConfiguration.getInstance() ;
		IndexConnection configES = new IndexConnection(esConfig.getHost(), esConfig.getPort());
		JestClient client = configES.client();
		
		// Search publications with Jest 		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("mimeType", "pdf"));
		System.out.println("Running the query\n" + searchSourceBuilder.query().toString());
		
		Search search = new Search.Builder(searchSourceBuilder.toString())
				// multiple index or types can be added.
				.addIndex(esConfig.getIndex())
				.addType(esConfig.getDocumentType())
				.setParameter(Parameters.SIZE, 100)
				.build();
		
		SearchResult resultSearch = client.execute(search);
		List<SearchResult.Hit<Publication, Void>> hits = resultSearch.getHits(Publication.class);
		
		Iterator<Hit<Publication, Void>> itPub = hits.iterator();	
		while (itPub.hasNext()) {
			Hit<Publication, Void> hit = itPub.next();
			System.out.println(hit.source);				
		}
		
		System.out.println("Results in json::" + resultSearch.getJsonString());
		
		client.shutdownClient();
		
	}

}
