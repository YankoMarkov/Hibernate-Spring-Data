package softuni.productshop.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.productshop.util.FileUtil;
import softuni.productshop.util.FileUtilImpl;
import softuni.productshop.util.ValidatorUtil;
import softuni.productshop.util.ValidatorUtilImpl;

@Configuration
public class ApplicationBeanConfiguration {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public FileUtil file() {
		return new FileUtilImpl();
	}
	
	@Bean
	public Gson gson() {
		return new GsonBuilder().setPrettyPrinting().create();
	}
	
	@Bean
	public ValidatorUtil validator() {
		return new ValidatorUtilImpl();
	}
}
