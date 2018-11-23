package softuni.bookshop.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtilImpl implements FileUtil {
	
	@Override
	public String[] getFileContent(File filePath) throws IOException {
		
		BufferedReader bfr = new BufferedReader(new FileReader(filePath));
		
		List<String> lines = new ArrayList<>();
		String line = null;
		
		while ((line = bfr.readLine()) != null) {
			lines.add(line);
		}
		
		return lines.stream()
				.filter(a -> !a.equals("")).toArray(String[]::new);
	}
}
