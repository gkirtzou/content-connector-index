package eu.openminted.content.index;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/**
 * @author gkirtzou
 *
 */
public class IndexConnection {
	
	private JestClient client;
	
	public IndexConnection() {
		JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200")              
                .multiThreaded(true)
                .build());
        this.client = factory.getObject();
	}
	
	public IndexConnection(String host, String port) {
		JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder(host+":" + port)              
                .multiThreaded(true)
                .build());
        this.client = factory.getObject();
		
	}

	public JestClient getClient() {
        return client;
}
	public void disconnect() {
		this.client.shutdownClient();
	}
	

}
