package softuni.bookshop.util;

import java.io.File;
import java.io.IOException;

public interface FileUtil {
	
	String[] getFileContent(File filePath) throws IOException;
}
