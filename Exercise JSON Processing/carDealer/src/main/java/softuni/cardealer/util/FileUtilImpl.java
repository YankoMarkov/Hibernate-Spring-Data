package softuni.cardealer.util;

import java.io.*;

public class FileUtilImpl implements FileUtil {
	
	@Override
	public String readFile(File filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		StringBuilder jsonString = new StringBuilder();
		String line = null;
		
		while ((line = reader.readLine()) != null) {
			jsonString.append(line);
		}
		return jsonString.toString();
	}
	
	@Override
	public void writeFile(String content, File filePath) throws IOException {
		try (Writer fileWriter = new FileWriter(filePath)) {
			fileWriter.write(content);
		}
	}
}
