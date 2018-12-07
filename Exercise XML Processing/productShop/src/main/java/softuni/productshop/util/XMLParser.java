package softuni.productshop.util;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public interface XMLParser {
	
	<T> T readXML(Class<T> objectClass, String filePath) throws IOException, JAXBException;
	
	<T> void writeXML(T object, Class<T> objectClass, String filePath) throws JAXBException;
}