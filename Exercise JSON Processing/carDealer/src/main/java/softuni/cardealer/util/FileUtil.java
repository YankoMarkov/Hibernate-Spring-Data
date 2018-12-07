package softuni.cardealer.util;

import java.io.File;
import java.io.IOException;

public interface FileUtil {
	
	String readFile(File filePath) throws IOException;
	
	void writeFile(String content, File filePath) throws IOException;
}