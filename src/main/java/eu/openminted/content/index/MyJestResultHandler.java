/**
 * 
 */
package eu.openminted.content.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;

/**
 * @author gkirtzou
 *
 */
public class MyJestResultHandler implements JestResultHandler<JestResult> {
		
	  private static final Logger log = LoggerFactory.getLogger(MyJestResultHandler.class);
	  @Override
	  public void completed(JestResult result) {
		  log.info("Result completed::" + result.getJsonString());	                		    	
	  }
	  
	  @Override
	  public void failed(Exception ex) {
		  log.info("Result failed::" + ex.getMessage());
	  }

}
