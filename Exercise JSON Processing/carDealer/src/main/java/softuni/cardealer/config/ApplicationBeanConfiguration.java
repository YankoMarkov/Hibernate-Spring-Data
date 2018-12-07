package softuni.cardealer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.cardealer.util.FileUtil;
import softuni.cardealer.util.FileUtilImpl;
import softuni.cardealer.util.ValidatorUtil;
import softuni.cardealer.util.ValidatorUtilImpl;

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
