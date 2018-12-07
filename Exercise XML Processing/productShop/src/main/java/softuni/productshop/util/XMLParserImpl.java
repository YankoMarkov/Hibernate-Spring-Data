package softuni.productshop.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XMLParserImpl implements XMLParser {
	
	@Override
	public <T> T readXML(Class<T> objectClass, String filePath) throws IOException, JAXBException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
		JAXBContext context = JAXBContext.newInstance(objectClass);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (T) unmarshaller.unmarshal(reader);
	}
	
	@Override
	public <T> void writeXML(T object, Class<T> objectClass, String filePath) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(objectClass);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(object, new File(filePath));
	}
}
