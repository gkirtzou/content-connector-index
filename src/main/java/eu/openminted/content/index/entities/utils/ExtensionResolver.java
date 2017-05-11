package eu.openminted.content.index.entities.utils
;

import java.io.File;

/*
 * A very basic resolver for file extension. 
 * It only returns .pdf 
 */
public class ExtensionResolver {

	static public String getExtension(File file) {
		return ".pdf";
	}
	
	static public String getExtension(String mimeType) {
		return ".pdf";
	}
}
