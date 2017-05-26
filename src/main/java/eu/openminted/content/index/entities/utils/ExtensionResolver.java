package eu.openminted.content.index.entities.utils
;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

/*
 * A very basic resolver for file extension. 
 * Information about mimetype and extension :: https://www.sitepoint.com/web-foundations/mime-types-complete-list/ 
 */
public class ExtensionResolver {

	static public String getExtension(File file) {
		String mimeType = new MimetypesFileTypeMap().getContentType(file);
		return ExtensionResolver.getExtension(mimeType);
	}
	
	static public String getExtension(String mimeType) {
		if (mimeType.equalsIgnoreCase("application/pdf")) {
			return ".pdf";
		}
		else if (mimeType.equalsIgnoreCase("application/xml")) {
			return ".xml";
		}
		else if (mimeType.equalsIgnoreCase("text/xml")) {
			return ".xml";
		}
		return ".xml";
	}
}
